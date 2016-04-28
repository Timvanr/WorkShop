package workshop.dao.mysql;

import workshop.DatabaseConnection;
import workshop.model.Klant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlantDAO implements workshop.dao.KlantDAOInterface{
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	protected static Logger logger = LoggerFactory.getLogger(KlantDAO.class);
	private static Connection connection;
	
	public KlantDAO() {
		getConnection();
	}

	public static Connection getConnection(){
		return DatabaseConnection.getPooledConnection();		
	}

	public void createTable() throws SQLException{
		connection = getConnection();

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
	
	@Override
	public int createKlant(Klant klant) {
		connection = getConnection();
		logger.info("CreataKlant(Klant klant); gestart.");
		String insertKlantNaamString = "INSERT INTO Klant (voornaam, tussenvoegsel, achternaam, email) values (?,?,?,?);";
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
				logger.info("CreateKlant(Klant klant); uitgevoerd:"
						+ "klant met " + klantId + "toegevoegd");
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				insertKlantNaam.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}	
		return klantId;
	}
	/*
	public void createKlantEnAdres(Klant klant, Adres adres) {
		Adreslijst al = new Adreslijst();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
	}

	@Override
	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) {
		Adreslijst al = new Adreslijst();
		Bestellijst bl = new Bestellijst();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
		bl.voegBestellingToe(klant_id, bestelling);

	}

	@Override
	public void createKlantEnBestelling(Klant klant, Bestelling bestelling) {
		Bestellijst bl = new Bestellijst();
		int klant_id = createKlant(klant);
		bl.voegBestellingToe(klant_id, bestelling);

	}
	*/
	
	@Override
	public Klant readKlantWithId(int klant_id){
		connection = getConnection();
		logger.info("readKlantWithId(int klant_id); gestart");		
		String query = "SELECT * FROM Klant WHERE klant_id =?";
		Klant klant = null;

		try{
			RowSet rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(query);
			rowSet.setInt(1, klant_id);
			rowSet.execute();

			while (rowSet.next()) {
				klant = new Klant();
				klant.setId(klant_id);
				klant.setVoornaam(rowSet.getString("voornaam"));
				klant.setTussenvoegsel(rowSet.getString("tussenvoegsel"));
				klant.setAchternaam(rowSet.getString("achternaam"));
				klant.setEmail(rowSet.getString("email"));
				logger.info("readKlantWithId(int klant_id) ); uitgevoerd"
						+ klant.toString() + "gelezen");
			}			
			
			rowSet.close();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klant;
	}

	@Override
	public Set<Klant> readKlantWithFirstName(String voornaam) {
		connection = getConnection();
		logger.info("readKlantWithFirstName(String voornaam); gestart");		
		LinkedHashSet<Klant> klantset = new LinkedHashSet<>();
		String query = "Select * from Klant where voornaam='" + voornaam + "'";
		Klant klant = null;
		RowSet rowSet = null;

		try {
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(query);
			rowSet.execute();

			if (!rowSet.isBeforeFirst()) {
				System.out.println("There are no records with firstname: " + voornaam);
				logger.info("Geen gegevens gevonden");
				return klantset;
			}

			while (rowSet.next()) {
				klant = new Klant();
				klant.setId(rowSet.getInt("klant_id"));
				klant.setVoornaam(rowSet.getString("voornaam"));
				klant.setTussenvoegsel(rowSet.getString("tussenvoegsel"));
				klant.setAchternaam(rowSet.getString("achternaam"));
				klant.setEmail(rowSet.getString("email"));
				klantset.add(klant);
				logger.info("readKlantWithFirstName(String voornaam); uitgevoerd");
			}

		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				rowSet.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
		return klantset;
	}
	
	@Override
	public Set<Klant> readKlantByAchternaam(String achter){
		connection = getConnection();
		String query = "SELECT * FROM Klant WHERE achternaam = ?";
		Set<Klant> klantSet = new HashSet();
		RowSet klantenByAchternaam = null;
		try {
			klantenByAchternaam = new JdbcRowSetImpl(connection);
			klantenByAchternaam.setCommand(query);
			klantenByAchternaam.setString(1, achter);
			klantenByAchternaam.execute();
			
			while (klantenByAchternaam.next()){
				Klant k = new Klant();
				k.setId(klantenByAchternaam.getInt("klant_id"));
				k.setVoornaam(klantenByAchternaam.getString("voornaam"));
				k.setTussenvoegsel(klantenByAchternaam.getString("tussenvoegsel"));
				k.setAchternaam(achter);
				k.setEmail(klantenByAchternaam.getString("email"));
				klantSet.add(k);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				klantenByAchternaam.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return klantSet;
	}

	public Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam){
		connection = getConnection();
		logger.info("readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam); gestart");		
		String query2 = "Select * from Klant where voornaam='" + voornaam + "' AND tussenvoegsel='" + tussenvoegsel
				+ "' AND achternaam='" + achternaam + "'";
		Klant klant = null;
		RowSet rowSet2 = null;
		try {
			rowSet2 = new JdbcRowSetImpl(connection);
			rowSet2.setCommand(query2);
			rowSet2.execute();

			if (!rowSet2.isBeforeFirst()) {
				System.out.println("There are no records with this combination of names: " + voornaam + " "
						+ tussenvoegsel + " " + achternaam);
				logger.info("Geen gegevens gevonden");
				return klant;
			} else {
				while (rowSet2.next()) {
					klant = new Klant();
					klant.setId(rowSet2.getInt("klant_id"));
					klant.setVoornaam(rowSet2.getString("voornaam"));
					klant.setTussenvoegsel(rowSet2.getString("tussenvoegsel"));
					klant.setAchternaam(rowSet2.getString("achternaam"));
					klant.setEmail(rowSet2.getString("email"));	
					logger.info("readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam); uitgevoerd");		
				}
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				rowSet2.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klant;
	}


	public void UpdateKlantNaam(int klant_id, Klant klant){
		connection = getConnection();
		logger.info("UpdateKlantNaam(int klant_id, Klant klant); gestart");
		String updateKlantNaamquery = "UPDATE Klant SET voornaam=?, achternaam=?, tussenvoegsel=? WHERE klant_id=?;";
		PreparedStatement updateKlantNaam = null;
		
		try{
			updateKlantNaam = connection.prepareStatement(updateKlantNaamquery);
			updateKlantNaam.setString(1, klant.getVoornaam());
			updateKlantNaam.setString(2, klant.getAchternaam());
			updateKlantNaam.setString(3, klant.getTussenvoegsel());
			updateKlantNaam.setInt(4, klant_id);			
			updateKlantNaam.executeUpdate();
			logger.info("UpdateKlantNaam(int klant_id); uitgevoerd");
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		finally {
			try {
				updateKlantNaam.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
	}

	public void updateEmail(int klant_id, String email){
		connection = getConnection();
		logger.info("updateEmail(int klant_id); gestart"); 
		PreparedStatement updateEmail = null;
		try {
			updateEmail = connection.prepareStatement("UPDATE Klant SET email = ? WHERE klant_id = ?");
		} catch (SQLException ez) {
			logger.error(ez.getMessage());
			ez.printStackTrace();
		}

		try {
			updateEmail.setString(1, email);
			updateEmail.setInt(2, klant_id);
			updateEmail.executeUpdate();
			logger.info("updateEmail(int klant_id); uitgevoerd"); 

		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		try {
			updateEmail.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		System.out.println("E-mailadres is veranderd!");
		System.out.println();
	}

	// WILLEN WE HIER ALLEEN EEN NAAM OF OOK EEN ADRES? ANDERS MOET DIT NOG AANGEPAST WORDEN...
	public Set<Klant> readAll(){
		connection = getConnection();
		LinkedHashSet<Klant> klantList = new LinkedHashSet();		
		String query = "Select * from `Klant`";

		try{ 
			RowSet rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(query);			
			rowSet.execute();

			while(rowSet.next()){
				Klant klant = new Klant();
				klant.setId(rowSet.getInt(1));
				klant.setVoornaam(rowSet.getString(2));
				klant.setTussenvoegsel(rowSet.getString(3));
				klant.setAchternaam(rowSet.getString(4));
				klant.setEmail(rowSet.getString(5));

				klantList.add(klant);	
			}
			rowSet.close();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klantList;
	}

	public void deleteAllFromKlantId(int klant_id){
		connection = getConnection();
		logger.info("deleteAllFromKlantId(int klant_id); gestart");
		PreparedStatement deleteFromId = null;
		String query = "Delete FROM Klant WHERE klant_id=?;";
		try{
			deleteFromId = connection.prepareStatement(query);
			deleteFromId.setInt(1, klant_id);			
			deleteFromId.executeUpdate();
			logger.info("deleteAllFromKlantId(int klant_id); uitgevoerd");


		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		finally{			
			try {
				deleteFromId.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel){
		connection = getConnection();
		logger.info("deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel); gestart");
		PreparedStatement deleteAllFromNaam = null;
		String query = "Delete FROM Klant WHERE voornaam='" + voornaam + "' AND tussenvoegsel='" + tussenvoegsel
				+ "' AND achternaam='" + achternaam + "';";

		try{
			deleteAllFromNaam = connection.prepareStatement(query);
			/*deleteAllFromNaam.setString(1, voornaam);
			deleteAllFromNaam.setString(2, achternaam);
			deleteAllFromNaam.setString(3, tussenvoegsel);	*/		
			deleteAllFromNaam.executeUpdate();
			logger.info("deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel); uitgevoerd");

		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		finally{			
			try {
				deleteAllFromNaam.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
