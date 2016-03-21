import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sql.RowSet;

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.rowset.JdbcRowSetImpl;
//ReadKlantWithAddress
//ReadKlantWithStraatnaam			Moeten deze in klant of in adres
//ReadKlantWithPostcodeHuisnummer
public class Klantbestand implements KlantDAO{
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	private final static String URL = "jdbc:mysql://localhost/Adresboek";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "komt_ie";
	
	private static Connection connection;
	
	public Klantbestand() {
		// Ensure the connection is initialized
		try {
			getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection() throws SQLException{
		if (connection == null || connection.isClosed())
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.out.print("Driver loaded... ");
				connection = DriverManager.getConnection("jdbc:mysql://localhost/Adresboek", "root", "komt_ie");
				System.out.println("Database connected!");
			
			}catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		return connection;
	}
	
	public void close() throws SQLException{
		connection.close();
		System.out.println("Connection closed!");
	}
	
	public void createTable() throws SQLException{
		getConnection();
		
		Statement createTable = connection.createStatement();
		createTable.execute("CREATE TABLE Klant (" +
				"klant_id INT AUTO_INCREMENT PRIMARY KEY, " +
				"voornaam VARCHAR(45), " +
				"tussenvoegsel VARCHAR(8), " +
				"achternaam VARCHAR(45), " +
				"adres_id INT, " +
				"email VARCHAR(180)" +
				")");
		close();
		
		System.out.println("Table Klant created!");
	}
		
	public void createKlant() throws SQLException {

		int klantId = 0;
		PreparedStatement insertKlantNaam = null;
		ResultSet rs = null;

		try {
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam;
		voornaam = input.readLine();
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
			getConnection();
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

		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			insertKlantNaam.close();
		}

	}
	public void createKlantEnAdres() throws IOException, SQLException {

		int klantId = 0;
		int adresId = 0;
		PreparedStatement insertKlantNaam = null;
		PreparedStatement insertAdres = null;
		ResultSet rs = null;
		ResultSet rsAdres = null;
		try{
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
		

		getConnection();
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
		} catch (IOException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			rsAdres.close();
			insertKlantNaam.close();
			insertAdres.close();
			close();
		}
	}

	public void createKlantEnAdresEnBestelling() throws SQLException {
		int klantId = 0;
		int adresId = 0;
		int bestellingId = 0;
		PreparedStatement insertKlantNaam = null;
		PreparedStatement insertAdres = null;
		PreparedStatement voegToe = null;
		ResultSet rs = null;
		ResultSet rsAdres = null;
		ResultSet rsBestelling = null;
		try{
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

		getConnection();
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

		} catch (IOException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			rsAdres.close();
			rsBestelling.close();
			insertKlantNaam.close();
			insertAdres.close();
			voegToe.close();
			close();
		}

	}

	public void createKlantEnBestelling() throws SQLException {
		Bestelling bestelling = new Bestelling();
		int klantId = 0;
		int bestellingId = 0;
		PreparedStatement insertKlantNaam = null;
		PreparedStatement voegToe = null;
		ResultSet rs = null;
		ResultSet rsBestelling = null;

		try{
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
		
			getConnection();
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
		catch (IOException ex){
			ex.printStackTrace();
		}
	catch (SQLException ex){
	
		ex.printStackTrace();
	}
	 finally {
		rs.close();
		rsBestelling.close();
		insertKlantNaam.close();
		voegToe.close();
		close();
	}

	}
	
	public Klant readKlantWithId(int klant_id) throws SQLException {
		String query = "SELECT * FROM klant WHERE klant_id =?";

		Klant klant = null;
				
		try (RowSet rowSet = new JdbcRowSetImpl()){
			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
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
			rowSet.setPassword(PASSWORD);
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
			rowSet2.setPassword(PASSWORD);
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

	
	public void UpdateKlantNaam(int klant_id) throws SQLException{
		String updateKlantNaamquery = "UPDATE klant SET voornaam=?, achternaam=?, tussenvoegsel=?, WHERE klant_id=?;";
		PreparedStatement updateKlantNaam = null;
		String newVoornaam = null;
		String newAchternaam = null;
		String newTussenvoegsel = null;
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Nieuwe voornaam");
			newVoornaam = input.readLine();
			System.out.println("Nieuwe achternaam");
			newAchternaam = input.readLine();
			System.out.println("Nieuw tussenvoegsel");
			newTussenvoegsel = input.readLine();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		try{
			getConnection();
			updateKlantNaam = connection.prepareStatement(updateKlantNaamquery);
			updateKlantNaam.setString(1, newVoornaam);
			updateKlantNaam.setString(2, newAchternaam);
			updateKlantNaam.setString(3, newTussenvoegsel);
			updateKlantNaam.setInt(4, klant_id);
			
			updateKlantNaam.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			updateKlantNaam.close();
			close();
		}
	}
	
	public void updateEmail(int klant_id) throws SQLException{
		getConnection();
		EmailValidator emailVal = EmailValidator.getInstance();
		PreparedStatement updateEmail = connection.prepareStatement
				("UPDATE Klant SET email = ? WHERE klant_id = ?");
		
		System.out.println("Nieuw Email adres");
		boolean invalidInput = true;
		String newEmail = null;
		try{
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
		
		try {
		updateEmail.setString(1, newEmail);
		updateEmail.setInt(2, klant_id);
		updateEmail.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		close();
		
		System.out.println("E-mailadres is veranderd!");
		System.out.println();
	}
	// WILLEN WE HIER ALLEEN EEN NAAM OF OOK EEN ADRES? ANDERS MOET DIT NOG AANGEPAST WORDEN...
	public ArrayList<Klant> readAll() throws SQLException{
		ArrayList<Klant> klantList = new ArrayList<>();
		
		String query = "Select * from `klant`";
		
		try (RowSet rowSet = new JdbcRowSetImpl()){
			
			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
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
		return klantList;
	}
	public void deleteAllFromKlantId(int klant_id) throws SQLException{
		getConnection();
		PreparedStatement deleteFromId = null;
		String query = "Delete FROM `klant`, `bestelling` USING `klant`, `bestelling` WHERE klant.klant_id=bestelling.klant_id AND klant.klant_id=?;";
		
		try{
			getConnection();
			deleteFromId = connection.prepareStatement(query);
			deleteFromId.setInt(1, klant_id);
			
			deleteFromId.executeUpdate();
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			close();
			deleteFromId.close();
		}
	}
	
	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel) throws SQLException{
		getConnection();
		PreparedStatement deleteAllFromNaam = null;
		String query = "Delete FROM klant, bestelling USING klant, bestelling WHERE klant.klant_id=bestelling.klant_id AND klant.voornaam=? AND klant.achternaam=? AND klant.tussenvoegsel=?;";
		
		try{
			getConnection();
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
			close();
			deleteAllFromNaam.close();
		}
	}

	
}
