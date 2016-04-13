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
import org.firebirdsql.*;

public class KlantDAOFireBird extends Klantbestand implements KlantDAO {
	
	
	public static Connection getConnection(){
		final String DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver";
		final String USERNAME = "SYSDBA";
		final String pw = "MasterKey";
		final String URL = "jdbc:firebirdsql://localhost:3050/D:/Workshop.gdb";
		Connection connection = null;
		
		try{
			Class.forName(DRIVER_CLASS);
			if (connection == null)
				connection = DriverManager.getConnection(URL, USERNAME, pw);
				System.out.println("connection made");
		} catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}		
		return connection;		
	}
   	
	public KlantDAOFireBird() {     
	}
	
	@Override
	public int createKlant(Klant klant) throws SQLException {
		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) "
				+ "values (?,?,?,?) RETURNING klant_id ;";
		PreparedStatement insertKlantNaam = null;
		ResultSet rs = null;
		int klantId = 0;
		
		try {
			Connection connection = getConnection();
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString);
			insertKlantNaam.setString(1, klant.getVoornaam());
			insertKlantNaam.setString(2, klant.getTussenvoegsel());
			insertKlantNaam.setString(3, klant.getAchternaam());
			insertKlantNaam.setString(4, klant.getEmail());
			ResultSet resultSet = insertKlantNaam.executeQuery();
			while (resultSet .next()) {
				klantId = resultSet.getInt("klant_id");
				System.out.println("Klant met klantnummer " + klantId + " is succesvol aangemaakt");
			}						
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {

		}
		return klantId;
	}
	
	public void createKlantEnAdres(Klant klant, Adres adres) throws SQLException {
		AdresDAOFireBird al = new AdresDAOFireBird();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
	}

	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) throws SQLException {
		AdresDAOFireBird al = new AdresDAOFireBird();
		Bestellijst bl = new Bestellijst();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
		bl.voegBestellingToe(bestelling);

	}

	public void createKlantEnBestelling(Klant klant, Bestelling bestelling) throws SQLException {
		Bestellijst bl = new Bestellijst();
		int klant_id = createKlant(klant);
		bl.voegBestellingToe(bestelling);

	}
	
	public Klant readKlantWithId(int klant_id) throws SQLException {
		String query = "SELECT * FROM klant WHERE klant_id =?";
		Klant klant = null;
				
		try{
			Connection connection = getConnection();
			RowSet rowSet = new JdbcRowSetImpl(connection);
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
			rowSet.close();
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
			Connection connection = getConnection();
			rowSet = new JdbcRowSetImpl(connection);
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
			Connection connection = getConnection();
			rowSet2 = new JdbcRowSetImpl(connection);
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
			Connection connection = getConnection();
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
			
		}
	}
	
	public void updateEmail(int klant_id) throws SQLException{
		Connection connection = getConnection();
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
		updateEmail.close();		
		System.out.println("E-mailadres is veranderd!");
		System.out.println();
	}
	
	// WILLEN WE HIER ALLEEN EEN NAAM OF OOK EEN ADRES? ANDERS MOET DIT NOG AANGEPAST WORDEN...
	public ArrayList<Klant> readAll() throws SQLException{
		ArrayList<Klant> klantList = new ArrayList<>();
		
		String query = "Select * from `klant`";
		
		try{ 
			Connection connection = getConnection();
			RowSet rowSet = new JdbcRowSetImpl(connection);
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
				rowSet.close();
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return klantList;
	}
	public void deleteAllFromKlantId(int klant_id) throws SQLException{
		PreparedStatement deleteFromId = null;
		String query = "Delete FROM `klant`, `bestelling` USING `klant`, `bestelling` WHERE klant.klant_id=bestelling.klant_id AND klant.klant_id=?;";
		
		try{
			Connection connection = getConnection();
			deleteFromId = connection.prepareStatement(query);
			deleteFromId.setInt(1, klant_id);
			
			deleteFromId.executeUpdate();
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			
			deleteFromId.close();
		}
	}
	
	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel) throws SQLException{
		PreparedStatement deleteAllFromNaam = null;
		String query = "Delete FROM klant, bestelling USING klant, bestelling WHERE klant.klant_id=bestelling.klant_id AND klant.voornaam=? AND klant.achternaam=? AND klant.tussenvoegsel=?;";
		
		try{
			Connection connection = getConnection();
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
			
			deleteAllFromNaam.close();
		}
	}

}
