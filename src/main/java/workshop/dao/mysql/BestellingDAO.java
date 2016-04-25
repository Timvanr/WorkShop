package workshop.dao.mysql;

import java.sql.*;
import java.sql.Date;

import workshop.DatabaseConnection;
import workshop.model.*;
import com.sun.rowset.*;
import java.util.*;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

public class BestellingDAO implements workshop.dao.BestellingDAOInterface {
	private static Connection connection;
	//ArtikelDAO artikellijst;
	
	public BestellingDAO() {
		getConnection();
	}
	
	private static Connection getConnection(){
		connection = DatabaseConnection.getPooledConnection();
		return connection;
	}
	/*
	public void maakTabel() throws SQLException {
		connection = DatabaseConnection.getPooledConnection();

		Statement createTable = connection.createStatement();

		createTable.executeUpdate(
				"CREATE TABLE Bestelling (" + "bestelling_id INT AUTO_INCREMENT, " + "klant_id INT NOT NULL, "
						+ "artikel1_id INT, " + "artikel1_naam VARCHAR(255), " + "artikel1_aantal INT, "
						+ "artikel1_prijs FLOAT, " + "artikel2_id INT, " + "artikel2_naam VARCHAR(255), "
						+ "artikel2_aantal INT, " + "artikel2_prijs FLOAT, " + "artikel3_id INT, "
						+ "artikel3_naam VARCHAR(255), " + "artikel3_aantal INT, " + "artikel3_prijs FLOAT, "
						+ "PRIMARY KEY (bestelling_id), " + "FOREIGN KEY (klant_id) REFERENCES Klant(klant_id))");

		System.out.println("table Bestellingen created!");
	}
/* niet nodig dit:
	public Bestelling createBestelling(int artikel1_id, int artikel1_aantal, int artikel2_id, int artikel2_aantal,
			int artikel3_id, int artikel3_aantal) throws SQLException {

		Bestelling bestelling = new Bestelling();
		ArtikelLijst aLijst = new ArtikelLijst();

		Artikel artikel1 = aLijst.getArtikelWithArtikelId(artikel1_id);
		Artikel artikel2 = aLijst.getArtikelWithArtikelId(artikel2_id);
		Artikel artikel3 = aLijst.getArtikelWithArtikelId(artikel3_id);

		if (artikel1 != null) {
			artikel1.setAantal(artikel1_aantal);
			bestelling.voegArtikelToeAanBestelling(artikel1);
		} else {
			return bestelling;
		}

		if (artikel2 != null) {
			artikel2.setAantal(artikel2_aantal);
			bestelling.voegArtikelToeAanBestelling(artikel2);
		} else {
			return bestelling;
		}
		if (artikel3 != null) {
			artikel3.setAantal(artikel3_aantal);
			bestelling.voegArtikelToeAanBestelling(artikel3);
		} else {
			return bestelling;
		}

		return bestelling;

	}
*/
	@Override
	public void voegBestellingToe(Bestelling bestelling){
		connection = getConnection();
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			PreparedStatement voegToe = connection.prepareStatement
					("INSERT INTO Bestelling (klant_id, datum_aanmaak) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			voegToe.setInt(1, bestelling.getKlant_id());
			voegToe.setDate(2, new Date(0, 0, 0));
			voegToe.executeUpdate();
			
			ResultSet rs = voegToe.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				bestelling.setBestelling_id(rs.getInt(1));
			}
			voegToe.close();
			
			for (Map.Entry<Artikel, Integer> entrySet: artikelen.entrySet()) {
				PreparedStatement insertArtikelen = connection.prepareStatement(
						"INSERT INTO bestelling_has_artikel " +
						"(bestelling_id, artikel_id, artikel_aantal) " +
						"VALUES (?, ?, ?)");
				insertArtikelen.setInt(1, bestelling.getBestelling_id());
				insertArtikelen.setInt(2, entrySet.getKey().getId());
				insertArtikelen.setInt(3, entrySet.getValue());
				insertArtikelen.executeUpdate();
				insertArtikelen.close();
			}
						
			System.out.println("Bestelling added!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
	}
		
	@Override
	public Bestelling getBestelling(int bestelling_id) throws SQLException{
		connection = DatabaseConnection.getPooledConnection();
		Bestelling bestelling = null;
		ArtikelDAO artikellijst = new ArtikelDAO();
		RowSet bestelData = null;
		try{
			bestelling = new Bestelling();
			bestelData = new JdbcRowSetImpl(connection);
			bestelData.setCommand(
					"SELECT * FROM Bestelling " +
					"INNER JOIN bestelling_has_artikel " +
					"ON Bestelling.bestelling_id=bestelling_has_artikel.bestelling_id " +
					"WHERE Bestelling.bestelling_id=?");
			bestelData.setInt(1, bestelling_id);
			bestelData.execute();
			
			bestelling.setKlant_id(bestelData.getInt("klant_id"));
			bestelling.setBestelling_id(bestelling_id);
			
			while (bestelData.next()){
				bestelling.voegArtikelToe
						(artikellijst.getArtikelWithArtikelId(bestelData.getInt("artikel_id")), bestelData.getInt("artikel_aantal"));
					
			}	 
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			bestelData.close();
		}
		
		return bestelling;
	}
	
	@Override
	public Set<Bestelling> haalBestellijst(){
		connection = DatabaseConnection.getPooledConnection();
		RowSet rowSet = null;
		Set<Bestelling> bestellijst = new LinkedHashSet();
		try {
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand("SELECT * FROM Bestelling");
			rowSet.execute();

			while (rowSet.next()) {
				bestellijst.add(getBestelling(rowSet.getInt(1)));
			}
			rowSet.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return bestellijst;
	}

	@Override
	public void verwijderBestelling(int bestelling_id){
		PreparedStatement verwijder = null;
		try {
			connection = DatabaseConnection.getPooledConnection();
			verwijder = connection.prepareStatement(
					"DELETE FROM Bestelling, bestelling_has_artikel" + 
					" USING Bestelling INNER JOIN bestelling_has_artikel ON" + 
					" bestelling.bestelling_id=bestelling_has_artikel.bestelling_id WHERE bestelling.bestelling_id=?");
			verwijder.setInt(1, bestelling_id);
			verwijder.executeUpdate();
			
			verwijder.close();
			System.out.println("Bestelling " + bestelling_id + " is verwijderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
	}


	@Override
	public int haalKlant_id(int bestelling_id){
		PreparedStatement haalKlant_id = null;
		int klant_id = 0;
		try {
			connection = DatabaseConnection.getPooledConnection();
			haalKlant_id = connection.prepareStatement
					("SELECT klant_id FROM Bestelling WHERE bestelling_id = ?");
			haalKlant_id.setInt(1, bestelling_id);
			ResultSet rs = haalKlant_id.executeQuery();

			if (rs.next()) {
				klant_id = rs.getInt(1);
			} else {
				System.out.println("Bestelling niet gevonden; kan geen bijbehorende klant retourneren");
			}
			haalKlant_id.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return klant_id;
	}
	
	public Set<Bestelling> getBestellijstByKlant(int klant_id){
		connection = DatabaseConnection.getPooledConnection();
		
		Set<Bestelling> bestellijst = new LinkedHashSet();		
		try {
			RowSet bestellijstByKlant = new JdbcRowSetImpl(connection);
			bestellijstByKlant.setCommand(
					"SELECT * FROM Bestelling " +
					"WHERE klant_id = ?");
			bestellijstByKlant.setInt(1, klant_id);
			
			while (bestellijstByKlant.next()){
				bestellijst.add(getBestelling(bestellijstByKlant.getInt(1)));
			}
			
			bestellijstByKlant.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return bestellijst;
	}
	/*
	public void verwijderTabel() throws SQLException {
		connection = DatabaseConnection.getPooledConnection();

		Statement dropTable = connection.createStatement();
		dropTable.executeUpdate("DROP TABLE Bestelling");

		System.out.println("table Bestelling dropped!");
	}
	*/
	public void close(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed!");
	}

}
