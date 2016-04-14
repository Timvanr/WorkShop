import java.sql.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;


public class Adreslijst implements AdresDAO {
	static final Logger LOG = LoggerFactory.getLogger(Adreslijst.class);
	
	public Adreslijst(){
				
	}
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
						
		return connection;
	}
	
	public void createTable() throws SQLException{
		Connection connection = getConnection();
		
		try {
			Statement createTable = connection.createStatement();
			createTable.execute("CREATE TABLE Adres (" +
					"adres_id INT AUTO_INCREMENT PRIMARY KEY, " +
					"straatnaam VARCHAR(45), " +
					"huisnummer INT, " +
					"toevoeging VARCHAR(6), " +
					"postcode VARCHAR(6), " +
					"woonplaats VARCHAR(45), " +
					"CONSTRAINT uniek UNIQUE (postcode, huisnummer, toevoeging)" +
					")");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		System.out.println("Table Adres created!");
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres){
		Connection connection = DatabaseConnection.getPooledConnection();
		
		String insertAdresString = "INSERT INTO Adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?);";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		ResultSet rs = null;
		
		try {
			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
			LOG.debug(insertAdres.toString());
			insertKlantHasAdres = connection.prepareStatement(insertKlantHasAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			insertAdres.executeUpdate();
			System.out.println("Adres created!");
			
			rs = insertAdres.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				insertKlantHasAdres.setInt(1, klant_id);
				insertKlantHasAdres.setInt(2, rs.getInt(1));
				insertKlantHasAdres.executeUpdate();
			}
		
		} catch (SQLException ex) {
			//System.out.println(ex.getErrorCode());
			if (ex.getErrorCode() == 1062){
				try {
					int existingAdres_id = readAdresMetPostcodeEnHuisnummer
							(adres.getPostcode(), adres.getHuisnummer(), adres.getToevoeging()).getId();
					
					getConnection();//connectie openen omdat die gesloten is door searchByPostcodeAndHuisnummer!
					PreparedStatement assignExistingAdres = connection.prepareStatement(insertKlantHasAdresString);
					assignExistingAdres.setInt(1, klant_id);
					assignExistingAdres.setInt(2, existingAdres_id);
					assignExistingAdres.executeUpdate();
					System.out.println("Adres already exists; existing Adres was assigned to Klant!");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				ex.printStackTrace();
			}
		} finally {
			try {
				insertAdres.close();
				insertKlantHasAdres.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}


	@Override
	public Adres readAdresmetAdresId(int adres_id) {
		Connection connection = getConnection();
		
		String query = "SELECT * FROM Adres WHERE adres_id =?";
		Adres adres = new Adres();
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
	
			rowSet.setCommand(query);
			rowSet.setInt(1, adres_id);
			rowSet.execute();
	
			while (rowSet.next()) {

				adres.setId(rowSet.getInt("adres_id"));
				adres.setStraatnaam(rowSet.getString("straatnaam"));
				adres.setHuisnummer(rowSet.getInt("huisnummer"));
				adres.setToevoeging(rowSet.getString("toevoeging"));
				adres.setWoonplaats(rowSet.getString("woonplaats"));
			}		
			rowSet.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adres;
	}

	
	@Override
	public void updateAdres(int klant_id, Adres adres){
		
		createAdres(klant_id, adres);
		
		System.out.println("Adresgegevens gewijzigd");
	}

	@Override
	public Set<Adres> readAdressenPerKlant(int klant_id){
		Connection connection = getConnection();
		
		Set<Adres> adressen = new LinkedHashSet();
		try {
			RowSet adressenPerKlant = new JdbcRowSetImpl(connection);
			adressenPerKlant.setCommand
					("SELECT * FROM Adres " +
					"INNER JOIN klant_has_adres " +
					"ON klant_has_adres.adres_id=Adres.adres_id " +
					"WHERE klant_has_adres.klant_id=? " +
					"ORDER BY woonplaats ASC, straatnaam ASC");
			adressenPerKlant.setInt(1, klant_id);
			adressenPerKlant.execute();
			
			while (adressenPerKlant.next()){
				adressen.add(new Adres(adressenPerKlant.getInt(1), adressenPerKlant.getString(2), adressenPerKlant.getInt(3), 
						adressenPerKlant.getString(4), adressenPerKlant.getString(5), adressenPerKlant.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return adressen;
	}
	
	@Override
	public Set<Adres> readAll() {
		Connection connection = getConnection();

		Set<Adres> adresLijst = new LinkedHashSet<>();
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "Select * from Adres ORDER BY woonplaats ASC, straatnaam ASC";
			rowSet.setCommand(query);
			rowSet.execute();
			
			while(rowSet.next()){
				Adres adres = new Adres();
				adres.setId(rowSet.getInt(1));
				adres.setStraatnaam(rowSet.getString(2));
				adres.setHuisnummer(rowSet.getInt(3));
				adres.setToevoeging(rowSet.getString(4));//staat in mijn tabel hier
				adres.setPostcode(rowSet.getString(5));
				adres.setWoonplaats(rowSet.getString(6));
				
				adresLijst.add(adres);			
			}
			rowSet.close();
			
		} catch (SQLException ex){
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return adresLijst;
	}

	//overloaded zonder toevoeging
	public Adres readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer){
		return readAdresMetPostcodeEnHuisnummer(postcode, huisnummer, null);
	}
	
	@Override
	public Set<Adres> readAdresMetWoonplaats(String plaats){
		Connection connection = getConnection();
		
		Set<Adres> adressen = new LinkedHashSet();
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE woonplaats = ? ORDER BY straatnaam ASC");
			adresData.setString(1, plaats);
			adresData.execute();
			
			while (adresData.next()){
				adressen.add(new Adres
						(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
			}
			adresData.close();
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return adressen;
	}

	@Override
	public Set<Adres> readAdresMetStraat(String straat, String plaats) {
		Connection connection = getConnection();
		
		Set<Adres> adressen = new LinkedHashSet();
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE straatnaam = ? AND woonplaats = ? ORDER BY huisnummer ASC");
			adresData.setString(1, straat);
			adresData.setString(2, plaats);
			adresData.execute();
			
			while (adresData.next()){
				adressen.add(new Adres(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
			}
			adresData.close();
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return adressen;
	}

	@Override
	public Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String toevoeging, String plaats) {
		Set<Adres> adressen = new LinkedHashSet();
		for (Adres a: readAdresMetStraat(straat, plaats)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}		
		return adressen;
	}
	
	//overloaded zonder toevoeging
	public Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String plaats) {
		return readAdresMetStraatEnHuisnummer(straat, huisnummer, null, plaats);
	}

	@Override
	public Set<Klant> readKlantenMetAdresId(int adres_id) {
		Connection connection = getConnection();
		
		Set<Klant> klantSet = new LinkedHashSet<>();
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "SELECT * FROM `Klant` " +
					"INNER JOIN klant_has_adres " +
					"ON Klant.klant_id = klant_has_adres.klant_id " +
					"WHERE klant_has_adres.adres_id = ? " +
					"ORDER BY achternaam ASC";
			rowSet.setCommand(query);
			rowSet.setInt(1, adres_id);
			rowSet.execute();

			while (rowSet.next()) {
				Klant klant = new Klant();
				klant.setId(rowSet.getInt(1));
				klant.setVoornaam(rowSet.getString(2));
				klant.setAchternaam(rowSet.getString(3));
				klant.setTussenvoegsel(rowSet.getString(4));
				klant.setEmail(rowSet.getString(5));
				
				klantSet.add(klant);
			}
			rowSet.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klantSet;
	}

	@Override
	public void deleteAdres(int id) {
		Connection connection = getConnection();
		
		try {		
			PreparedStatement deleteAdres = connection.prepareStatement
					("DELETE * FROM Adres WHERE adres_id = ?");
			deleteAdres.setInt(1, id);
			deleteAdres.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Adres is verwijderd!");
	}
	
	public void deleteKlantAdresPair(int klant_id, int adres_id){
		Connection connection = getConnection();		
		try {
			PreparedStatement deleteKlantAdresPair = connection.prepareStatement
					("DELETE * FROM klant_has_adres WHERE klant_id = ? AND adres_id = ?");
			deleteKlantAdresPair.setInt(1, klant_id);
			deleteKlantAdresPair.setInt(2, adres_id);
			deleteKlantAdresPair.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Klant-Adres-koppeling is verwijderd!");
	}

}
