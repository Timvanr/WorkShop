
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;
import java.sql.PreparedStatement;

import org.apache.commons.validator.routines.*;
public class KlantDAO {
	
	final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	final static String USERNAME = "sandermegens";
	final static String pw = "FrIkandel";
	final static String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";
	
	
	public static void createKlant(Klant klant) {

		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email, straatnaam, postcode, toevoeging, huisnummer, woonplaats) values (?,?,?,?,?,?,?,?,?);";
		String insertBestellingString = "INSERT INTO bestelling (artikel1_id, artikel1_naam, artikel1_prijs, artikel1_aantal, artikel2_id, "
				+ "artikel2_naam, artikel2_prijs, artikel2_aantal, artikel3_id, artikel3_naam, artikel3_prijs, artikel3_aantal, klant_id) values (?,?,?,?,?,?,?,?,?,?,?,?, LAST_INSERT_ID());";
		
		PreparedStatement insertKlantNaam = null;
		PreparedStatement insertBestelling = null;
		Connection connection = null;
		
		try {
			connection = DatabaseConnection.getConnection();
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString);
			
			insertBestelling = connection.prepareStatement(insertBestellingString);
			connection.setAutoCommit(false);
			
			if (klant.heeftAdres() == false && klant.heeftBestelling() == false) {
				insertKlantNaam.setString(1, klant.getVoornaam());
				insertKlantNaam.setString(2, klant.getTussenvoegsel());
				insertKlantNaam.setString(3, klant.getAchternaam());
				insertKlantNaam.setString(4, klant.getEmail());

				insertKlantNaam.executeUpdate();
				//connection.commit();
			} else if (klant.heeftAdres() == true && klant.heeftBestelling() == false) {
				insertKlantNaam.setString(1, klant.getVoornaam());
				insertKlantNaam.setString(2, klant.getTussenvoegsel());
				insertKlantNaam.setString(3, klant.getAchternaam());
				insertKlantNaam.setString(4, klant.getEmail());
				insertKlantNaam.setString(5, klant.adres.getStraatnaam());
				insertKlantNaam.setString(6, klant.adres.getPostcode());
				insertKlantNaam.setString(7, klant.adres.getToevoeging());
				insertKlantNaam.setInt(8, klant.adres.getHuisnummer());
				insertKlantNaam.setString(9, klant.adres.getWoonplaats());
				insertKlantNaam.executeUpdate();
				
				connection.commit();
			}
			else if (klant.heeftAdres() == false && klant.heeftBestelling() == true){
				insertKlantNaam.setString(1, klant.getVoornaam());
				insertKlantNaam.setString(2, klant.getTussenvoegsel());
				insertKlantNaam.setString(3, klant.getAchternaam());
				insertKlantNaam.setString(4, klant.getEmail());
				insertKlantNaam.executeUpdate();
				insertBestelling.setInt(1, klant.bestelling.getArtikel1_id());
				insertBestelling.setString(2, klant.bestelling.getArtikel1_naam());
				insertBestelling.setInt(3, klant.bestelling.getArtikel1_aantal());
				insertBestelling.setInt(4, klant.bestelling.getArtikel1_prijs());
				insertBestelling.setInt(5, klant.bestelling.getArtikel2_id());
				insertBestelling.setString(6, klant.bestelling.getArtikel2_naam());
				insertBestelling.setInt(7, klant.bestelling.getArtikel2_aantal());
				insertBestelling.setInt(8, klant.bestelling.getArtikel2_prijs());
				insertBestelling.setInt(9, klant.bestelling.getArtikel3_id());
				insertBestelling.setString(10, klant.bestelling.getArtikel3_naam());
				insertBestelling.setInt(11, klant.bestelling.getArtikel3_aantal());
				insertBestelling.setInt(12, klant.bestelling.getArtikel3_prijs());
				insertBestelling.executeUpdate();
				connection.commit();
			}
			else if (klant.heeftAdres() == true && klant.heeftBestelling() == true){
				insertKlantNaam.setString(1, klant.getVoornaam());
				insertKlantNaam.setString(2, klant.getTussenvoegsel());
				insertKlantNaam.setString(3, klant.getAchternaam());
				insertKlantNaam.setString(4, klant.getEmail());
				insertKlantNaam.setString(5, klant.adres.getStraatnaam());
				insertKlantNaam.setString(6, klant.adres.getPostcode());
				insertKlantNaam.setString(7, klant.adres.getToevoeging());
				insertKlantNaam.setInt(8, klant.adres.getHuisnummer());
				insertKlantNaam.setString(9, klant.adres.getWoonplaats());
				insertKlantNaam.executeUpdate();
				insertBestelling.setInt(1, klant.bestelling.getArtikel1_id());
				insertBestelling.setString(2, klant.bestelling.getArtikel1_naam());
				insertBestelling.setInt(3, klant.bestelling.getArtikel1_aantal());
				insertBestelling.setInt(4, klant.bestelling.getArtikel1_prijs());
				insertBestelling.setInt(5, klant.bestelling.getArtikel2_id());
				insertBestelling.setString(6, klant.bestelling.getArtikel2_naam());
				insertBestelling.setInt(7, klant.bestelling.getArtikel2_aantal());
				insertBestelling.setInt(8, klant.bestelling.getArtikel2_prijs());
				insertBestelling.setInt(9, klant.bestelling.getArtikel3_id());
				insertBestelling.setString(10, klant.bestelling.getArtikel3_naam());
				insertBestelling.setInt(11, klant.bestelling.getArtikel3_aantal());
				insertBestelling.setInt(12, klant.bestelling.getArtikel3_prijs());
				insertBestelling.executeUpdate();
				connection.commit();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				System.out.println();
				connection.close();
				insertKlantNaam.close();
				insertBestelling.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

	}

	public static Klant readKlantWithId(int klant_id) throws SQLException {
		String query = "SELECT * FROM klant WHERE klant_id =?";

		Klant klant = null;
		RowSet rowSet = new JdbcRowSetImpl();
		
		try {
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);
			rowSet.setInt(1, klant_id);
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
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
		}

		return klant;
	}

	public static Klant readKlantWithFirstName(String voornaam) throws SQLException {
		String query = "Select * from klant where voornaam='" + voornaam + "'";

		Klant klant = null;
		RowSet rowSet = null;

		try {
			rowSet = new JdbcRowSetImpl();
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);

			rowSet.execute();

			ResultSetMetaData rowSetMD = rowSet.getMetaData();

			if (!rowSet.isBeforeFirst()) {
				System.out.println("There are no records with firstname: " + voornaam);
				return klant;
			}

			for (int i = 1; i <= rowSetMD.getColumnCount(); i++) {
				System.out.printf("%-12s\t", rowSetMD.getColumnLabel(i));
			}
			System.out.println();

			while (rowSet.next()) {
				for (int i = 1; i <= rowSet.getMetaData().getColumnCount(); i++) {
					System.out.printf("%-12s\t", rowSet.getObject(i));
					if (i % rowSetMD.getColumnCount() == 0) {
						System.out.println();
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
			System.out.println();
		}
		return klant;

	}

	public static Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam)
			throws SQLException {
		String query2 = "Select * from klant where voornaam='" + voornaam + "' AND tussenvoegsel='" + tussenvoegsel
				+ "' AND achternaam='" + achternaam + "'";

		Klant klant = null;
		RowSet rowSet2 = null;

		try {
			rowSet2 = new JdbcRowSetImpl();
			rowSet2.setUrl(URL);
			rowSet2.setPassword(pw);
			rowSet2.setUsername(USERNAME);
			rowSet2.setCommand(query2);

			rowSet2.execute();

			ResultSetMetaData rowSetMD = rowSet2.getMetaData();

			if (!rowSet2.isBeforeFirst()) {
				System.out.println("There are no records with this combination of names: " + voornaam + " "
						+ tussenvoegsel + " " + achternaam);
				return klant;
			}

			for (int i = 1; i <= rowSetMD.getColumnCount(); i++) {
				System.out.printf("%-12s\t", rowSetMD.getColumnLabel(i));
			}
			System.out.println();

			while (rowSet2.next()) {
				for (int i = 1; i <= rowSet2.getMetaData().getColumnCount(); i++) {
					System.out.printf("%-12s\t", rowSet2.getObject(i));
					if (i % rowSetMD.getColumnCount() == 0) {
						System.out.println();
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
			rowSet2.close();
		}
		return klant;
	}

	public static void readKlantWithAddress(String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats) throws SQLException{
		
		RowSet rowSet = new JdbcRowSetImpl();
		String query = "Select * from `klant` where straatnaam=? AND huisnummer=? AND toevoeging=? AND postcode=? AND woonplaats=?";
		
		try{
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);
			rowSet.setString(1, straatnaam);
			rowSet.setInt(2, huisnummer);
			rowSet.setString(3, toevoeging);
			rowSet.setString(4, postcode);
			rowSet.setString(5, woonplaats);
			
			rowSet.execute();
			
			if (!rowSet.next()){
				System.out.println("no entries found with this address");
				return;
			}
			else {
				ResultSetMetaData rsMD = rowSet.getMetaData();
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
				}
				System.out.println();
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rowSet.getString(i));
					
					if (i % rsMD.getColumnCount() == 0){
						System.out.println();
					}
				}
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
			}
		finally {
			rowSet.close();
			System.out.println();
		}
		
		}
	
	public static void readKlantWithStraatnaam(String straatnaam) throws SQLException{
		
		String query = "Select * from `klant` where straatnaam='" + straatnaam + "'";
		RowSet rowSet = null;
		
		try{
			rowSet = new JdbcRowSetImpl();
			rowSet.setUrl(URL);
			rowSet.setUsername(USERNAME);
			rowSet.setPassword(pw);
			rowSet.setCommand(query);
			rowSet.execute();
			
			if (!rowSet.isBeforeFirst()){
				System.out.println("no records were found with: " + straatnaam);
				return;
			}
			
			else {
				ResultSetMetaData rsMD = rowSet.getMetaData();
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
				}
				System.out.println();
				while (rowSet.next()){
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rowSet.getObject(i));
					
					if (i % rsMD.getColumnCount() == 0){
						System.out.println();
					}
				}
				}
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			System.out.println();
			rowSet.close();
		}
	}
	
public static void readKlantWithPostCodeHuisnummer(String postcode, int huisnummer) throws SQLException{
		
		String query = "Select * from `klant` where postcode=? AND huisnummer=?";
		RowSet rowSet = new JdbcRowSetImpl();
		
		try{
			
			rowSet.setUrl(URL);
			rowSet.setUsername(USERNAME);
			rowSet.setPassword(pw);
			rowSet.setCommand(query);
			rowSet.setString(1, postcode);
			rowSet.setInt(2, huisnummer);
			rowSet.execute();
			
			if (!rowSet.isBeforeFirst()){
				System.out.println("no records were found with: " + postcode + " and huisnummer " + huisnummer);
				return;
			}
			
			else {
				ResultSetMetaData rsMD = rowSet.getMetaData();
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
				}
				System.out.println();
				while (rowSet.next()){
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rowSet.getObject(i));
					
					if (i % rsMD.getColumnCount() == 0){
						System.out.println();
					}
				}
				}
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			System.out.println();
			rowSet.close();
		}
	}
	
public static ArrayList<Klant> readAll() throws SQLException{
	ArrayList<Klant> klantList = new ArrayList<>();
	RowSet rowSet = new JdbcRowSetImpl();
	String query = "Select * from `klant`";
	
	try{
		
		rowSet.setUrl(URL);
		rowSet.setPassword(pw);
		rowSet.setUsername(USERNAME);
		rowSet.setCommand(query);
		
		rowSet.execute();
			
		while(rowSet.next()){
			Klant klant = new Klant();
			klant.setId(rowSet.getInt(1));
			klant.setVoornaam(rowSet.getString(2));
			klant.setAchternaam(rowSet.getString(3));
			klant.setTussenvoegsel(rowSet.getString(4));
			klant.setEmail(rowSet.getString(5));
			klant.adres.setStraatnaam(rowSet.getString(6));
			klant.adres.setHuisnummer(rowSet.getInt(9));
			klant.adres.setPostcode(rowSet.getString(7));
			klant.adres.setToevoeging(rowSet.getString(8));
			klant.adres.setWoonplaats(rowSet.getString(10));
			
			klantList.add(klant);			
		}
	}
	catch (SQLException ex){
		ex.printStackTrace();
	}
	finally {
		rowSet.close();
	}
	return klantList;
}
	
	public static void UpdateKlantNaam(int klant_id) throws IOException, SQLException{
		String updateKlantNaamquery = "UPDATE klant SET voornaam=?, achternaam=?, tussenvoegsel=?, email=? WHERE klant_id=?;";
		Connection connection = null;
		PreparedStatement updateKlantNaam = null;
		String newVoornaam = null;
		String newAchternaam = null;
		String newTussenvoegsel = null;
		String newEmail = null;
		
		try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
			EmailValidator emailVal = EmailValidator.getInstance();
			boolean invalidInput = true;
			System.out.println("enter new voornaam");
			newVoornaam = input.readLine();
			System.out.println("enter new achternaam");
			newAchternaam = input.readLine();
			System.out.println("enter new tussenvoegsel");
			newTussenvoegsel = input.readLine();
			System.out.println("enter new Email adres");
			
			while(invalidInput){
				newEmail = input.readLine();
				if (emailVal.isValid(newEmail)){
					invalidInput = false;
				}
				else {
					System.out.println("incorrect E-mail address. Please retry");
				}
				
			}
			
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		try{
			connection = DatabaseConnection.getConnection();
			updateKlantNaam = connection.prepareStatement(updateKlantNaamquery);
			updateKlantNaam.setString(1, newVoornaam);
			updateKlantNaam.setString(2, newAchternaam);
			updateKlantNaam.setString(3, newTussenvoegsel);
			updateKlantNaam.setString(4, newEmail);
			updateKlantNaam.setInt(5, klant_id);
			
			updateKlantNaam.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			updateKlantNaam.close();
			connection.close();
		}
	}
		
		public static void UpdateKlantAddress(int klant_id) throws IOException, SQLException{
			String updateAdresQuery = "UPDATE klant SET straatnaam=?, huisnummer=?, toevoeging=?, postcode=?, woonplaats=? WHERE klant_id=?;";
			Connection connection = null;
			PreparedStatement updateAdres = null;
			String newStraatnaam = null;
			String newHuisnummerString = null;
			String newToevoeging = null;
			String newPostcode = null;
			String newWoonplaats = null;
			int newHuisnummer = 0;
			
			try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
				
				System.out.println("enter new straatnaam");
				newStraatnaam = input.readLine();
				System.out.println("enter new huisnummer");
				newHuisnummerString = input.readLine();
				newHuisnummer = Integer.parseInt(newHuisnummerString);
				System.out.println("enter new toevoeging");
				newToevoeging = input.readLine();
				System.out.println("enter new postcode");
				newPostcode = input.readLine();
				System.out.println("enter new woonplaats");
				newWoonplaats = input.readLine();
			}
			catch (IOException ex){
				ex.printStackTrace();
			}
			try{
				connection = DatabaseConnection.getConnection();
				updateAdres = connection.prepareStatement(updateAdresQuery);
				updateAdres.setString(1, newStraatnaam);
				updateAdres.setInt(2, newHuisnummer);
				updateAdres.setString(3, newToevoeging);
				updateAdres.setString(4, newPostcode);
				updateAdres.setString(5, newWoonplaats);
				updateAdres.setInt(6, klant_id);
				
				updateAdres.executeUpdate();
			}
			catch (SQLException ex){
				ex.printStackTrace();
			}
			finally {
				updateAdres.close();
				connection.close();
			}
		
	}
		
	public static void updateKlantNaamAddress(int klant_id) throws SQLException, IOException{
		UpdateKlantNaam(klant_id);
		UpdateKlantAddress(klant_id);
	}


	public static void deleteAllFromKlantId(int klant_id) throws SQLException{
		Connection connection = null;
		PreparedStatement deleteFromId = null;
		String query = "Delete FROM `klant`, `bestelling` USING `klant`, `bestelling` WHERE klant.klant_id=bestelling.klant_id AND klant.klant_id=?;";
		
		try{
			connection = DatabaseConnection.getConnection();
			deleteFromId = connection.prepareStatement(query);
			deleteFromId.setInt(1, klant_id);
			
			deleteFromId.executeUpdate();
			
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			connection.close();
			deleteFromId.close();
		}
	}

	public static void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel) throws SQLException{
		Connection connection = null;
		PreparedStatement deleteAllFromNaam = null;
		String query = "Delete FROM klant, bestelling USING klant, bestelling WHERE klant.klant_id=bestelling.klant_id AND klant.voornaam=? AND klant.achternaam=? AND klant.tussenvoegsel=?;";
		
		try{
			connection = DatabaseConnection.getConnection();
			deleteAllFromNaam = connection.prepareStatement(query);
			deleteAllFromNaam.setString(1, voornaam);
			deleteAllFromNaam.setString(2, achternaam);
			deleteAllFromNaam.setString(3, tussenvoegsel);
			
			deleteAllFromNaam.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			connection.close();
			deleteAllFromNaam.close();
		}
	}
	
	
}