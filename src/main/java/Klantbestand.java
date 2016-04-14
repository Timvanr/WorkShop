import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import javax.sql.RowSet;
import org.apache.commons.validator.routines.EmailValidator;
import com.sun.rowset.JdbcRowSetImpl;

public class Klantbestand implements KlantDAO{
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	
	public Klantbestand() {
	}
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;		
	}
	
	public void createTable() throws SQLException{
		Connection connection = DatabaseConnection.getPooledConnection();
		
		Statement createTable = connection.createStatement();
		createTable.execute("CREATE TABLE Klant (" +
				"klant_id INT AUTO_INCREMENT PRIMARY KEY, " +
				"voornaam VARCHAR(45), " +
				"tussenvoegsel VARCHAR(8), " +
				"achternaam VARCHAR(45), " +
				"adres_id INT, " +
				"email VARCHAR(180)" +
				")");
		
		
		System.out.println("Table Klant created!");
	}
		
	public int createKlant(Klant klant) throws SQLException {
		Connection connection = getConnection();
		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";
		PreparedStatement insertKlantNaam = null;
		ResultSet rs = null;
		int klantId = 0;
		try {				
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString, Statement.RETURN_GENERATED_KEYS);
			insertKlantNaam.setString(1, klant.getVoornaam());
			insertKlantNaam.setString(2, klant.getTussenvoegsel());
			insertKlantNaam.setString(3, klant.getAchternaam());
			insertKlantNaam.setString(4, klant.getEmail());
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
			insertKlantNaam.close();
			connection.close();
		}	
		return klantId;
	}

	public void createKlantEnAdres(Klant klant, Adres adres) throws SQLException {
		Adreslijst al = new Adreslijst();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
	}

	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) throws SQLException {
		Adreslijst al = new Adreslijst();
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
				Connection connection = DatabaseConnection.getPooledConnection();
				RowSet rowSet = new JdbcRowSetImpl(connection);
				rowSet.setCommand(query);
				rowSet.setInt(1, klant_id);
				rowSet.execute();
				
				while (rowSet.next()) {
					klant = new Klant();
					klant.setVoornaam(rowSet.getString("voornaam"));
					klant.setTussenvoegsel(rowSet.getString("tussenvoegsel"));
					klant.setAchternaam(rowSet.getString("achternaam"));
					klant.setEmail(rowSet.getString("email"));
					
					}			
				rowSet.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return klant;
		}
	@Override
	public Set<Klant> readKlantWithFirstName(String voornaam) throws SQLException {
		LinkedHashSet<Klant> klantset = new LinkedHashSet<>();
		String query = "Select * from klant where voornaam='" + voornaam + "'";

		Klant klant = null;
		RowSet rowSet = null;

		try {
			Connection connection = DatabaseConnection.getPooledConnection();
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(query);

			rowSet.execute();

			if (!rowSet.isBeforeFirst()) {
				System.out.println("There are no records with firstname: " + voornaam);
				return klantset;
			}

			while (rowSet.next()) {
				klant = new Klant();
				klant.setVoornaam(rowSet.getString("voornaam"));
				klant.setTussenvoegsel(rowSet.getString("tussenvoegsel"));
				klant.setAchternaam(rowSet.getString("achternaam"));
				klant.setEmail(rowSet.getString("email"));
				klantset.add(klant);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
			System.out.println();
		}
		return klantset;

	}

	public Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam)
			throws SQLException {
		String query2 = "Select * from klant where voornaam='" + voornaam + "' AND tussenvoegsel='" + tussenvoegsel
				+ "' AND achternaam='" + achternaam + "'";

		Klant klant = null;
		RowSet rowSet2 = null;

		try {
			Connection connection = DatabaseConnection.getPooledConnection();
			rowSet2 = new JdbcRowSetImpl(connection);
			rowSet2.setCommand(query2);

			rowSet2.execute();

			if (!rowSet2.isBeforeFirst()) {
				System.out.println("There are no records with this combination of names: " + voornaam + " "
						+ tussenvoegsel + " " + achternaam);
				return klant;
			}

			while (rowSet2.next()) {
				klant = new Klant();
				klant.setVoornaam(rowSet2.getString("voornaam"));
				klant.setTussenvoegsel(rowSet2.getString("tussenvoegsel"));
				klant.setAchternaam(rowSet2.getString("achternaam"));
				klant.setEmail(rowSet2.getString("email"));				
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
		String[] newNaam = null;
		Service service = new Service();
		try {
			newNaam = service.completeNaamPrompt();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		try{
			Connection connection = DatabaseConnection.getPooledConnection();
			updateKlantNaam = connection.prepareStatement(updateKlantNaamquery);
			updateKlantNaam.setString(1, newNaam[0]);
			updateKlantNaam.setString(2, newNaam[2]);
			updateKlantNaam.setString(3, newNaam[1]);
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
		Connection connection = DatabaseConnection.getPooledConnection();
		Service service = new Service();
		String newEmail = null;
		PreparedStatement updateEmail = connection.prepareStatement
				("UPDATE Klant SET email = ? WHERE klant_id = ?");
		
		System.out.println("Nieuw Email adres");
		try{
			newEmail = service.emailPrompt();
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
	public Set<Klant> readAll() throws SQLException{
		LinkedHashSet<Klant> klantList = new LinkedHashSet<>();
		
		String query = "Select * from `klant`";
		
		try{ 
			Connection connection = DatabaseConnection.getPooledConnection();
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
			Connection connection = DatabaseConnection.getPooledConnection();
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
			Connection connection = DatabaseConnection.getPooledConnection();
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
