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
				"adres_id INT AUTO_INCREMENT PRIMARY KEY, " +//werk ik nou met MySQL of met Oracle of beiden?!?
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
	public void createAdres(Adres adres) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Adres readAdres(int adres_id) throws SQLException {
		getConnection();
		String query = "SELECT * FROM adres WHERE adres_id =?";

		Adres adres = null;
		RowSet rowSet = new JdbcRowSetImpl();

		try {
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);
			rowSet.setInt(1, adres_id);
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
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
		}

		return adres;
	}

	@Override
	public Set<Adres> readAll() throws SQLException {
		getConnection();
		Set<Adres> adresLijst = new LinkedHashSet<>();
		RowSet rowSet = new JdbcRowSetImpl();
		String query = "Select * from 'adres'";
		
		try {
			
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);
			
			rowSet.execute();
			
			while(rowSet.next()){
				Adres adres = new Adres();
				adres.setId(rowSet.getInt(1));
				adres.setStraatnaam(rowSet.getString(2));
				adres.setHuisnummer(rowSet.getInt(3));
				adres.setPostcode(rowSet.getString(4));
				adres.setWoonplaats(rowSet.getString(5));
				adres.setToevoeging(rowSet.getString(6));
	
				adresLijst.add(adres);			
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			rowSet.close();
		}
		return adresLijst;
	}

	@Override
	public Adres searchById(int id) throws SQLException {
		getConnection();
		
		PreparedStatement searchById = connection.prepareStatement
				("SELECT * FROM Adres WHERE adres_id = ?");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Adres> searchByStraat(String straat) throws SQLException {
		getConnection();
		
		RowSet adresData = new JdbcRowSetImpl(connection);
		adresData.setCommand("SELECT * FROM Adres WHERE straatnaam = ?");
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
			
		Set<Adres> adressenPerStraat = searchByStraat(straat);
		Set<Adres> adressen = new LinkedHashSet();
		
		for (Adres a: adressenPerStraat){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}
		
		return adressen;
	}

	@Override
	public Klant[] getKlant(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws SQLException {
		connection.close();
		System.out.println("Connection closed!");
		
	}
	

}
