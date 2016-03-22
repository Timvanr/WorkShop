import java.sql.*;
import java.util.*;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;


public class Adreslijst implements AdresDAO {
	private static Connection connection;
	
	public Adreslijst(){
		try {
			getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.print("Driver loaded... ");
			
				connection = DriverManager.getConnection("jdbc:mysql://localhost/Adresboek", "root", "komt_ie");
				System.out.println("Database connected!");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
				
		return connection;
	}
	
	public void createTable() throws SQLException{
		getConnection();
		
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
		
		close();
		System.out.println("Table Adres created!");
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres) throws SQLException {

		String insertAdresString = "INSERT INTO ADRES (straatnaam, huisnummer, toevoeging, postcode, woonplaats, klant_id) VALUES (?,?,?,?,?,?);";
		PreparedStatement insertAdres = null;

		try (Connection connection = DatabaseConnection.getConnection()) {

			insertAdres = connection.prepareStatement(insertAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			insertAdres.setInt(6, klant_id);
			insertAdres.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			insertAdres.close();
		}
	}


	@Override
	public Adres readAdres(int id) throws SQLException {
		getConnection();
		
		String query = "SELECT * FROM Adres WHERE adres_id =?";
		Adres adres = null;
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
		close();
		
		return adres;
	}
	
	@Override
	public void updateAdres(int klant_id, Adres adres) throws SQLException{//Wat doe ik hier nou weer?!?
		
		createAdres(klant_id, adres);
		
		Addressbook adresboek = new Addressbook();
		adresboek.addAdres_id(klant_id, Addressbook.haalKlant(klant_id).getAdres_id());//werkt dit? misschien addAdres_id static maken??
		
		close();
		System.out.println("Adresgegevens gewijzigd");
	}

	@Override
	public Set<Adres> readAll() throws SQLException {
		getConnection();

		Set<Adres> adresLijst = new LinkedHashSet<>();
		RowSet rowSet = new JdbcRowSetImpl(connection);
		String query = "Select * from Adres ORDER BY woonplaats ASC, straatnaam ASC";
		/*
		try {
			
			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
			rowSet.setUsername(USERNAME);
			*/
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
		/*
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
		*/
			rowSet.close();
		//}
		close();
		return adresLijst;
	}

	@Override
	public Adres searchById(int id) throws SQLException {
		getConnection();
		
		PreparedStatement searchById = connection.prepareStatement
				("SELECT * FROM Adres WHERE adres_id = ?");
		searchById.setInt(1, id);
		ResultSet adresInfo = searchById.executeQuery();
		
		Adres adres = null;
		if (adresInfo.next()){
			adres = new Adres(adresInfo.getString(2), adresInfo.getInt(3), adresInfo.getString(4), adresInfo.getString(5), adresInfo.getString(6));
		}else{
			System.out.println("Adres niet gevonden!");
		}
		
		close();
		
		return adres;
	}

	@Override
	public Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer,	String toevoeging) throws SQLException {
		getConnection();
		
		Adres adres = new Adres();
		RowSet rowSet = new JdbcRowSetImpl(connection);
		String query = "Select * from `Adres` where postcode=? AND huisnummer=? AND toevoeging=?;";
		/*
		try {
			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
			rowSet.setUsername(USERNAME);
			*/
			rowSet.setCommand(query);
			rowSet.setString(1, postcode);
			rowSet.setInt(2, huisnummer);
			rowSet.setString(3, toevoeging);

			rowSet.execute();

			if (!rowSet.next()) {
				System.out.println("no entries found with this address");
			} else {
				adres.setId(rowSet.getInt(1);
				adres.setStraatnaam(rowSet.getString(3));
				adres.setHuisnummer(rowSet.getInt(4));
				adres.setToevoeging(rowSet.getString(5));
				adres.setPostcode(rowSet.getString(6));
				adres.setWoonplaats(rowSet.getString(7));
										
			}
		/*										
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
		*/
			rowSet.close();
		//}
		return adres;
		}

	@Override
	public Set<Adres> searchByStraat(String straat) throws SQLException {
		getConnection();
		
		RowSet adresData = new JdbcRowSetImpl(connection);
		adresData.setCommand("SELECT * FROM Adres WHERE straatnaam = ? ORDER BY woonplaats ASC");
		adresData.setString(1, straat);
		adresData.execute();
		
		Set<Adres> adressen = new LinkedHashSet();
		while (adresData.next()){
			adressen.add(new Adres(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
		}
		adresData.close();
		close();
		
		return adressen;
	}

	@Override
	public Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging) throws SQLException {
				
		Set<Adres> adressen = new LinkedHashSet();
		
		for (Adres a: searchByStraat(straat)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}
		
		return adressen;
	}

	@Override
	public Set<Klant> getKlant(int adres_id) throws SQLException {
		getConnection();
		
		Set<Klant> klantSet = new LinkedHashSet<>();
		RowSet rowSet = new JdbcRowSetImpl(connection);
		String query = "Select * from `Klant` where adres_id=? order by achternaam asc;";
		/*
		try {

			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
			rowSet.setUsername(USERNAME);
			*/
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
			/*
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
		*/
			rowSet.close();
		//}
		return klantSet;
	}

	@Override
	public void deleteAdres(int id) throws SQLException {
		getConnection();
		
		PreparedStatement deleteAdres = connection.prepareStatement
				("DELETE * FROM Adres WHERE adres_id = ?");
		deleteAdres.setInt(1, id);
		deleteAdres.executeUpdate();
		
		close();
		System.out.println("Adres is verwijderd!");
	}
	
	@Override
	public void close() throws SQLException {
		connection.close();
		System.out.println("Connection closed!");
		
	}

}
