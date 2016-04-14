import java.sql.*;
import java.util.*;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;

public class AdresDAOFireBird implements AdresDAO {
	Connection connection = null;
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;
	}
   	
	public AdresDAOFireBird() {     
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres){
		getConnection();

		String insertAdresString = "INSERT INTO Adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?) RETURNING adres_id ;";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		int adres_id = 0;

		try {
			insertAdres = connection.prepareStatement(insertAdresString);
			insertKlantHasAdres = connection.prepareStatement(insertKlantHasAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			ResultSet resultSet = insertAdres.executeQuery();
			System.out.println("Adres created!");

			while (resultSet .next()) {
				adres_id = resultSet.getInt("adres_id");

				if (resultSet.isBeforeFirst()) {
					resultSet.next();
					insertKlantHasAdres.setInt(1, klant_id);
					insertKlantHasAdres.setInt(2, adres_id);
					insertKlantHasAdres.executeUpdate();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	} 	
	
	@Override
	public Adres readAdresmetAdresId(int id) {
		Connection connection = getConnection();
		String query = "SELECT * FROM adres WHERE adres_id =?";
		Adres adres = null;
				
		try{
			
			RowSet rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(query);
			rowSet.setInt(1, id);
			rowSet.execute();
			
			ResultSetMetaData rsMD = rowSet.getMetaData();

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
		} catch (SQLException ex) {
			ex.printStackTrace();
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
		
		Set<Adres> adressen = new LinkedHashSet<>();
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
			close();
		}
		
		return adresLijst;
	}

	@Override
	public Adres readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer,	String toevoeging) {
		Connection connection = getConnection();
		
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
		return readAdresMetPostcodeEnHuisnummer(postcode, huisnummer, null);
	}
	
	@Override
	public Set<Adres> readAdresMetWoonplaats(String plaats){
		Connection connection = getConnection();
		
		Set<Adres> adressen = new LinkedHashSet<>();
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
	public Set<Adres> readAdresMetStraat(String straat, String plaats) {
		Connection connection = getConnection();
		
		Set<Adres> adressen = new LinkedHashSet<>();
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
	public Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String toevoeging, String plaats) {
		Set<Adres> adressen = new LinkedHashSet<>();
		for (Adres a: readAdresMetStraat(straat, plaats)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}
		
		return adressen;
	}
	//overloaded zonder toevoeging
	public Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String plaats) {
		return readAdresMetStraatEnHuisnummer(straat, huisnummer, null, plaats);
	}

	@Override
	public Set<Klant> readKlantenMetAdresId(int adres_id) {
		Connection connection = getConnection();
		
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
			Connection connection = getConnection();
		
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
			Connection connection = getConnection();
			
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

