
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.validator.routines.*;
public class KlantDAO {
	
	final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	final static String USERNAME = "sandermegens";
	final static String pw = "FrIkandel";
	final static String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);

	public void createKlant() throws IOException, SQLException {

		int klantId = 0;
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();

		boolean invalidInput = true;
		EmailValidator emailVal = EmailValidator.getInstance();
		System.out.print("Emailadres: 	(geef 'x' in als u geen E-mailadres heeft)");
		String email = null;
		while (invalidInput) {
			email = input.readLine();
			if (email == "x") {
				email = null;
				break;
			} else if (emailVal.isValid(email)) {
				invalidInput = false;
			} else {
				System.out
						.println("E-mailadres voldoet niet aan de eisen voor een geldig E-mailadres, probeer opnieuw");
			}
		}

		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";

		PreparedStatement insertKlantNaam = null;
		ResultSet rs = null;

		try (Connection connection = DatabaseConnection.getConnection()) {
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString, Statement.RETURN_GENERATED_KEYS);

			insertKlantNaam.setString(1, voornaam);
			insertKlantNaam.setString(2, tussenvoegsel);
			insertKlantNaam.setString(3, achternaam);
			insertKlantNaam.setString(4, email);
			insertKlantNaam.executeUpdate();
			rs = insertKlantNaam.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				klantId = rs.getInt(1);
				System.out.println("Klant met klantnummer " + klantId + " is succesvol aangemaakt");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			insertKlantNaam.close();
		}

	}

	public void createKlantNaamEnAdres() throws IOException, SQLException {

		int klantId = 0;
		int adresId = 0;
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();

		boolean invalidInput = true;
		EmailValidator emailVal = EmailValidator.getInstance();
		System.out.print("Emailadres: ");
		String email = null;
		while (invalidInput) {
			email = scInput.next();
			if (emailVal.isValid(email)) {
				invalidInput = false;
				break;
			} else
				System.out
						.println("E-mailadres voldoet niet aan de eisen voor een geldig E-mailadres, probeer opnieuw");
		}
		System.out.print("Straatnaam: ");
		String straatnaam = input.readLine();
		System.out.print("Huisnummer: ");
		String huisnummerstr = input.readLine();
		int huisnummer = Integer.parseInt(huisnummerstr);
		System.out.print("Toevoeging: ");
		String toevoeging = input.readLine();
		System.out.print("Postcode: ");
		String postcode = input.readLine();
		System.out.print("Woonplaats: ");
		String woonplaats = input.readLine();

		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";
		String insertAdresString = "INSERT INTO ADRES (straatnaam, huisnummer, toevoeging, postcode, woonplaats, klant_id) VALUES (?,?,?,?,?,?);";
		PreparedStatement insertKlantNaam = null;
		PreparedStatement insertAdres = null;
		ResultSet rs = null;
		ResultSet rsAdres = null;

		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString, Statement.RETURN_GENERATED_KEYS);

			insertKlantNaam.setString(1, voornaam);
			insertKlantNaam.setString(2, tussenvoegsel);
			insertKlantNaam.setString(3, achternaam);
			insertKlantNaam.setString(4, email);
			insertKlantNaam.executeUpdate();
			rs = insertKlantNaam.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				klantId = rs.getInt(1);
			}
			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
			insertAdres.setString(1, straatnaam);
			insertAdres.setInt(2, huisnummer);
			insertAdres.setString(3, toevoeging);
			insertAdres.setString(4, postcode);
			insertAdres.setString(5, woonplaats);
			insertAdres.setInt(6, klantId);
			insertAdres.executeUpdate();
			rsAdres = insertAdres.getGeneratedKeys();
			if (rsAdres.isBeforeFirst()) {
				rsAdres.next();
				adresId = rsAdres.getInt(1);
			}
			connection.commit();
			System.out
					.println("Klant met klantnummer " + klantId + " en adres-id " + adresId + " succesvol aangemaakt");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			rsAdres.close();
			insertKlantNaam.close();
			insertAdres.close();
		}
	}

	public void createKlantEnAdresEnBestelling() throws IOException, SQLException {
		int klantId = 0;
		int adresId = 0;
		int bestellingId = 0;
		Bestelling bestelling = new Bestelling();
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();

		boolean invalidInput = true;
		EmailValidator emailVal = EmailValidator.getInstance();
		System.out.print("Emailadres: ");
		String email = null;
		while (invalidInput) {
			email = scInput.next();
			if (emailVal.isValid(email)) {
				invalidInput = false;
				break;
			} else
				System.out
						.println("E-mailadres voldoet niet aan de eisen voor een geldig E-mailadres, probeer opnieuw");
		}
		System.out.print("Straatnaam: ");
		String straatnaam = input.readLine();
		System.out.print("Huisnummer: ");
		String huisnummerstr = input.readLine();
		int huisnummer = Integer.parseInt(huisnummerstr);
		System.out.print("Toevoeging: ");
		String toevoeging = input.readLine();
		System.out.print("Postcode: ");
		String postcode = input.readLine();
		System.out.print("Woonplaats: ");
		String woonplaats = input.readLine();
		
		int keuze = 1;
		while (keuze == 1)  {
			System.out.println("Welke artikel wilt u toevoegen: ");
			System.out.println("Artikel id:");
			int id = scInput.nextInt();
			System.out.print("Artikel naam: ");
			String artikelnaam = scInput.next();
			System.out.print("Hoeveel wilt u van dit artkel: ");
			int aantal = scInput.nextInt();
			System.out.print("Wat is de prijs van dit artikel: ");
			BigDecimal prijs = scInput.nextBigDecimal();

			Artikel artikelAdd = new Artikel(id, artikelnaam, prijs, aantal);
			bestelling.artikelen.add(artikelAdd);
			System.out.print("Wilt u meer artikelen toevoegen (kies 1 voor ja en 2 voor nee)? ");
			keuze = scInput.nextInt();
		}

		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";
		String insertAdresString = "INSERT INTO ADRES (straatnaam, huisnummer, toevoeging, postcode, woonplaats, klant_id) VALUES (?,?,?,?,?,?);";

		PreparedStatement insertKlantNaam = null;
		PreparedStatement insertAdres = null;
		PreparedStatement voegToe = null;
		ResultSet rs = null;
		ResultSet rsAdres = null;
		ResultSet rsBestelling = null;

		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString, Statement.RETURN_GENERATED_KEYS);

			insertKlantNaam.setString(1, voornaam);
			insertKlantNaam.setString(2, tussenvoegsel);
			insertKlantNaam.setString(3, achternaam);
			insertKlantNaam.setString(4, email);
			insertKlantNaam.executeUpdate();
			rs = insertKlantNaam.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				klantId = rs.getInt(1);
			}
			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
			insertAdres.setString(1, straatnaam);
			insertAdres.setInt(2, huisnummer);
			insertAdres.setString(3, toevoeging);
			insertAdres.setString(4, postcode);
			insertAdres.setString(5, woonplaats);
			insertAdres.setInt(6, klantId);
			insertAdres.executeUpdate();
			rsAdres = insertAdres.getGeneratedKeys();
			if (rsAdres.isBeforeFirst()) {
				rsAdres.next();
				adresId = rsAdres.getInt(1);
			}

			String sql = "";
			String values = "";

			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				sql += String.format(", artikel%d_id, artikel%d_naam, artikel%d_aantal, artikel%d_prijs", i + 1, i + 1,
						i + 1, i + 1);
				values += ", ?, ?, ?, ?";
			}

			voegToe = connection.prepareStatement(
					String.format("INSERT INTO Bestelling (klant_id%s) VALUES (?%s)", sql, values),
					Statement.RETURN_GENERATED_KEYS);
			voegToe.setInt(1, klantId);
			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				voegToe.setInt(2 + i * 4, bestelling.artikelen.get(i).getId());
				voegToe.setString(3 + i * 4, bestelling.artikelen.get(i).getNaam());
				voegToe.setInt(4 + i * 4, bestelling.artikelen.get(i).getAantal());
				voegToe.setBigDecimal(5 + i * 4, bestelling.artikelen.get(i).getPrijs());
			}
			voegToe.executeUpdate();

			rsBestelling = voegToe.getGeneratedKeys();
			if (rsBestelling.isBeforeFirst()) {
				rsBestelling.next();
				bestellingId = rsBestelling.getInt(1);
			}
			connection.commit();
			System.out.println(
					"Klant met klantnummer " + klantId + ", adres-id " + adresId + " en bestelnummer" + bestellingId + " succesvol toegevoegd");
			connection.commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			rsAdres.close();
			rsBestelling.close();
			insertKlantNaam.close();
			insertAdres.close();
			voegToe.close();
		}

	}

	public void createKlantEnBestelling() throws IOException, SQLException {
		Bestelling bestelling = new Bestelling();
		int klantId = 0;
		int bestellingId = 0;
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();

		boolean invalidInput = true;
		EmailValidator emailVal = EmailValidator.getInstance();
		System.out.print("Emailadres: ");
		String email = null;
		while (invalidInput) {
			email = scInput.next();
			if (emailVal.isValid(email)) {
				invalidInput = false;
				break;
			} else
				System.out
						.println("E-mailadres voldoet niet aan de eisen voor een geldig E-mailadres, probeer opnieuw");
		}
		int keuze = 1;
		while (keuze == 1)  {
			System.out.println("Welke artikel wilt u toevoegen: ");
			System.out.println("Artikel id:");
			int id = scInput.nextInt();
			System.out.print("Artikel naam: ");
			String artikelnaam = scInput.next();
			System.out.print("Hoeveel wilt u van dit artkel: ");
			int aantal = scInput.nextInt();
			System.out.print("Wat is de prijs van dit artikel: ");
			BigDecimal prijs = scInput.nextBigDecimal();

			Artikel artikelAdd = new Artikel(id, artikelnaam, prijs, aantal);
			bestelling.artikelen.add(artikelAdd);
			System.out.print("Wilt u meer artikelen toevoegen (kies 1 voor ja en 2 voor nee)? ");
			keuze = scInput.nextInt();
		}

		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";
		PreparedStatement insertKlantNaam = null;
		PreparedStatement voegToe = null;
		ResultSet rs = null;
		ResultSet rsBestelling = null;

		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString, Statement.RETURN_GENERATED_KEYS);

			insertKlantNaam.setString(1, voornaam);
			insertKlantNaam.setString(2, tussenvoegsel);
			insertKlantNaam.setString(3, achternaam);
			insertKlantNaam.setString(4, email);
			insertKlantNaam.executeUpdate();
			rs = insertKlantNaam.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				klantId = rs.getInt(1);
			}
			String sql = "";
			String values = "";

			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				sql += String.format(", artikel%d_id, artikel%d_naam, artikel%d_aantal, artikel%d_prijs", i + 1, i + 1,
						i + 1, i + 1);
				values += ", ?, ?, ?, ?";
			}

			voegToe = connection.prepareStatement(
					String.format("INSERT INTO Bestelling (klant_id%s) VALUES (?%s)", sql, values), Statement.RETURN_GENERATED_KEYS);
			
			voegToe.setInt(1, klantId);
			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				voegToe.setInt(2 + i * 4, bestelling.artikelen.get(i).getId());
				voegToe.setString(3 + i * 4, bestelling.artikelen.get(i).getNaam());
				voegToe.setInt(4 + i * 4, bestelling.artikelen.get(i).getAantal());
				voegToe.setBigDecimal(5 + i * 4, bestelling.artikelen.get(i).getPrijs());
			}
			voegToe.executeUpdate();

			rsBestelling = voegToe.getGeneratedKeys();
			if (rsBestelling.isBeforeFirst()) {
				rsBestelling.next();
				bestellingId = rsBestelling.getInt(1);
			}
			connection.commit();
		

		System.out.println("Klant met klantnummer " + klantId + " en bestelnummer " + bestellingId + " succesvol aangemaakt");
	}
	catch (SQLException ex){
	
		ex.printStackTrace();
	}
	 finally {
		rs.close();
		rsBestelling.close();
		insertKlantNaam.close();
		voegToe.close();
	}

	}

	public Klant readKlantWithId(int klant_id) throws SQLException {
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

	public Klant readKlantWithFirstName(String voornaam) throws SQLException {
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

	public Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam)
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

	public void readKlantWithAddress(String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats) throws SQLException{
		
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
	
	public void readKlantWithStraatnaam(String straatnaam) throws SQLException{
		
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
	
public void readKlantWithPostCodeHuisnummer(String postcode, int huisnummer) throws SQLException{
		
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
	
public ArrayList<Klant> readAll() throws SQLException{
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
	
	public void UpdateKlantNaam(int klant_id) throws IOException, SQLException{
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
			System.out.println("Nieuwe voornaam");
			newVoornaam = input.readLine();
			System.out.println("Nieuwe achternaam");
			newAchternaam = input.readLine();
			System.out.println("Nieuw tussenvoegsel");
			newTussenvoegsel = input.readLine();
			System.out.println("Nieuw Email adres");
			
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
		
		public void UpdateKlantAddress(int klant_id) throws IOException, SQLException{
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
				
				System.out.println("Nieuwe straatnaam");
				newStraatnaam = input.readLine();
				System.out.println("Nieuw huisnummer");
				newHuisnummerString = input.readLine();
				newHuisnummer = Integer.parseInt(newHuisnummerString);
				System.out.println("Nieuwe toevoeging");
				newToevoeging = input.readLine();
				System.out.println("Nieuwe postcode");
				newPostcode = input.readLine();
				System.out.println("Nieuwe woonplaats");
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
		
	public void updateKlantNaamAddress(int klant_id) throws SQLException, IOException{
		UpdateKlantNaam(klant_id);
		UpdateKlantAddress(klant_id);
	}


	public void deleteAllFromKlantId(int klant_id) throws SQLException{
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

	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel) throws SQLException{
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