import java.sql.*;
import java.util.*;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;


public class Adreslijst implements AdresDAO {
	
	public Adreslijst(){
			
	}
	
	public void createTable(){
				
		try {
			Statement createTable = DatabaseConnection.getPooledConnection().createStatement();
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
		}
		System.out.println("Table Adres created!");
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres) throws SQLException {

		String insertAdresString = "INSERT INTO ADRES (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?);";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		ResultSet rs = null;
		try (Connection connection = DatabaseConnection.getPooledConnection()) {

			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
			insertKlantHasAdres = connection.prepareStatement(insertKlantHasAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			insertAdres.executeUpdate();
			
			rs = insertAdres.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				insertKlantHasAdres.setInt(1, klant_id);
				insertKlantHasAdres.setInt(2, rs.getInt(1));
				insertKlantHasAdres.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			insertAdres.close();
			insertKlantHasAdres.close();
		}
	}

	@Override
	public void readAdres(int klantid) throws SQLException {
		getConnection();
		String adresID = "SELECT adres_id FROM klant_has_adres WHERE klant_id =" + klantid;
		String query = "SELECT * FROM Adres WHERE adres_id =?";
		int adresid = 0;

		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(adresID);
			rowSet.execute();

			while(rowSet.next())
				adresid = rowSet.getInt(1);

			try {	

				RowSet rowSet2 = new JdbcRowSetImpl(connection);
				rowSet2.setCommand(query);
				rowSet2.setInt(1, adresid);
				rowSet2.execute();

				ResultSetMetaData rsMD = rowSet2.getMetaData();

				for (int i = 1; i <= rsMD.getColumnCount(); i++) {
					System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
				}
				System.out.println(); 
				while (rowSet2.next()) {
					for (int i = 1; i <= rowSet2.getMetaData().getColumnCount(); i++) {
						System.out.printf("%-12s\t", rowSet2.getObject(i));
					}
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				rowSet.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();			
		}
	}
	
	@Override
	public void updateAdres(int klant_id, Adres adres){//Wat doe ik hier nou weer?!?
		createAdres(klant_id, adres);
		
		Addressbook adresboek = new Addressbook();
		try {
			adresboek.addAdres_id(klant_id, Addressbook.haalKlant(klant_id).getAdres_id());//werkt dit? misschien addAdres_id static maken??
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {		

		}
		System.out.println("Adresgegevens gewijzigd");
	}

	@Override
	public Set<Adres> readAll() {
		

		Set<Adres> adresLijst = new LinkedHashSet<>();
		
		try {
			Connection connection = DatabaseConnection.getPooledConnection();
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
			
		}catch (SQLException ex){
			ex.printStackTrace();
		}finally {
			
		}
		
		return adresLijst;
	}

	@Override
	public Adres searchById(int id) {
		Connection connection = DatabaseConnection.getPooledConnection();
		
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
			searchById.close();
		}catch (SQLException ex){
			ex.printStackTrace();
		}finally {
			
		}
		
		return adres;
	}

	@Override
	public Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer,	String toevoeging) {
		Connection connection = DatabaseConnection.getPooledConnection();
		
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
				adres.setStraatnaam(rowSet.getString(3));
				adres.setHuisnummer(rowSet.getInt(4));
				adres.setToevoeging(rowSet.getString(5));
				adres.setPostcode(rowSet.getString(6));
				adres.setWoonplaats(rowSet.getString(7));
										
			}
			rowSet.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			
		}
		
		return adres;
	}
	
	@Override
	public Set<Adres> searchByWoonplaats(String plaats){
		Connection connection = DatabaseConnection.getPooledConnection();
		
		Set<Adres> adressen = new LinkedHashSet<>();
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE woonplaats = ? ORDER BY straatnaam ASC");
			adresData.setString(1, plaats);
			adresData.execute();
			
			while (adresData.next()){
				adressen.add(new Adres(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
			}
			adresData.close();
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

		}
		
		return adressen;
	}

	@Override
	public Set<Adres> searchByStraat(String straat, String plaats) {
		Connection connection = DatabaseConnection.getPooledConnection();
		
		Set<Adres> adressen = new LinkedHashSet<>();
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE straatnaam = ? AND woonplaats = ?");
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
			
		}
		
		return adressen;
	}

	@Override
	public Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging, String plaats) {
		Set<Adres> adressen = new LinkedHashSet<>();
		
		for (Adres a: searchByStraat(straat, plaats)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}
		
		return adressen;
	}

	@Override
	public Set<Klant> getKlant(int adres_id) {
		Connection connection = DatabaseConnection.getPooledConnection();
		
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
		}
		return klantSet;
	}

	@Override
	public void deleteAdres(int id) {
		try {
			Connection connection = DatabaseConnection.getPooledConnection();
		
			PreparedStatement deleteAdres = connection.prepareStatement
					("DELETE * FROM Adres WHERE adres_id = ?");
			deleteAdres.setInt(1, id);
			deleteAdres.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		}
		System.out.println("Adres is verwijderd!");
	}
	
}
