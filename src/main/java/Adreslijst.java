
import java.sql.*;
import java.util.*;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;


public class Adreslijst implements AdresDAO {
	private static Connection connection;
	
	public Adreslijst(){
		getConnection();
			
	}
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
						
		return connection;
	}
	
	public void createTable(){
		getConnection();
		
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
			close();
		}
		System.out.println("Table Adres created!");
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres){
		getConnection();
		
		String insertAdresString = "INSERT INTO Adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?);";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		ResultSet rs = null;
		
		try {
			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
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
					int existingAdres_id = searchByPostcodeAndHuisnummer
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			close();
		}
		
	}

	@Override
	public Adres readAdres(int id) {
		getConnection();
		
		String query = "SELECT * FROM Adres WHERE adres_id =?";
		Adres adres = null;
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
	
			rowSet.setCommand(query);
			rowSet.setInt(1, id);
			rowSet.execute();
	
			ResultSetMetaData rsMD = rowSet.getMetaData();
	
			// deze weergave is niet noodzakelijk maar vond ik wel netjes dus overgenomenv van Sander
			for (int i = 1; i <= rsMD.getColumnCount(); i++) {
				System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
			}
			System.out.println(); 
			while (rowSet.next()) {
				for (int i = 1; i <= rowSet.getMetaData().getColumnCount(); i++) {
					System.out.printf("%-12s\t", rowSet.getObject(i));
				}
			}
			
			rowSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return adres;
	}
	
	@Override
	public void updateAdres(int klant_id, Adres adres){
		
		createAdres(klant_id, adres);
		
		System.out.println("Adresgegevens gewijzigd");
	}

	@Override
	public Set<Adres> adressenPerKlant(int klant_id){
		getConnection();
		
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
			close();
		}
		
		return adressen;
	}
	
	@Override
	public Set<Adres> readAll() {
		getConnection();

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
			close();
		}
		
		return adresLijst;
	}

	@Override
	public Adres searchById(int id) {
		getConnection();
		
		Adres adres = null;
		try {
			PreparedStatement searchById = connection.prepareStatement
					("SELECT * FROM Adres WHERE adres_id = ?");
			searchById.setInt(1, id);
			ResultSet adresInfo = searchById.executeQuery();
			
			if (adresInfo.next()){
				adres = new Adres(adresInfo.getString(2), adresInfo.getInt(3), adresInfo.getString(4), adresInfo.getString(5), adresInfo.getString(6));
			}else{
				System.out.println("Adres niet gevonden!");
			}
			
		}catch (SQLException ex){
			ex.printStackTrace();
		}finally {
			close();
		}
		
		return adres;
	}

	@Override
	public Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer,	String toevoeging) {
		getConnection();
		
		Adres adres = new Adres();
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "Select * from `Adres` where postcode=? AND huisnummer=? AND toevoeging=?;";
			
			rowSet.setCommand(query);
			rowSet.setString(1, postcode);
			rowSet.setInt(2, huisnummer);
			rowSet.setString(3, toevoeging);

			rowSet.execute();

			if (!rowSet.next()) {
				System.out.println("no entries found with this address");
			} else {
				adres.setId(rowSet.getInt(1));
				adres.setStraatnaam(rowSet.getString(2));
				adres.setHuisnummer(rowSet.getInt(3));
				adres.setToevoeging(rowSet.getString(4));
				adres.setPostcode(rowSet.getString(5));
				adres.setWoonplaats(rowSet.getString(6));
										
			}
			rowSet.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		
		return adres;
	}
	//overloaded zonder toevoeging
	public Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer){
		return searchByPostcodeAndHuisnummer(postcode, huisnummer, null);
	}
	
	@Override
	public Set<Adres> searchByWoonplaats(String plaats){
		getConnection();
		
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
			close();
		}
		
		return adressen;
	}

	@Override
	public Set<Adres> searchByStraat(String straat, String plaats) {
		getConnection();
		
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
			close();
		}
		
		return adressen;
	}

	@Override
	public Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging, String plaats) {
		Set<Adres> adressen = new LinkedHashSet();
		for (Adres a: searchByStraat(straat, plaats)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}
		
		return adressen;
	}
	//overloaded zonder toevoeging
	public Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String plaats) {
		return searchByStraatAndHuisnummer(straat, huisnummer, null, plaats);
	}

	@Override
	public Set<Klant> getKlant(int adres_id) {
		getConnection();
		
		Set<Klant> klantSet = new LinkedHashSet<>();
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "Select * from `Klant` where adres_id=? order by achternaam asc;";
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
			close();
		}
		return klantSet;
	}

	@Override
	public void deleteAdres(int id) {
		try {
			getConnection();
		
			PreparedStatement deleteAdres = connection.prepareStatement
					("DELETE * FROM Adres WHERE adres_id = ?");
			deleteAdres.setInt(1, id);
			deleteAdres.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println("Adres is verwijderd!");
	}
	
	public void deleteKlantAdresPair(int klant_id, int adres_id){
		try {
			getConnection();
			
			PreparedStatement deleteKlantAdresPair = connection.prepareStatement
					("DELETE * FROM klant_has_adres WHERE klant_id = ? AND adres_id = ?");
			deleteKlantAdresPair.setInt(1, klant_id);
			deleteKlantAdresPair.setInt(2, adres_id);
			deleteKlantAdresPair.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println("Klant-Adres-koppeling is verwijderd!");
	}
	
	public void close() {
		try {
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed!");
		
	}

}
