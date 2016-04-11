


import java.sql.*;

import com.sun.rowset.*;
import java.util.*;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

public class Bestellijst implements BestellingDAO {
	private static Connection connection;
	
	public Bestellijst() {
		connection = DatabaseConnection.getPooledConnection();
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
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			connection = DatabaseConnection.getPooledConnection();
			PreparedStatement voegToe = connection.prepareStatement(
					"INSERT INTO Bestelling (klant_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			voegToe.setInt(1, bestelling.getKlant_id());
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
	/*
	public Bestelling haalBestelling(int bestelling_id) throws SQLException {
		RowSet rowSet = null;
		RowSet rowSet2 = null;
		String haalBestellingString = "SELECT artikel_id FROM Bestelling_has_artikel WHERE bestelling_id = ?";
		Bestelling bestelling = new Bestelling();
		ArtikelLijst aLijst = new ArtikelLijst();
		
		try {
			connection = DatabaseConnection.getPooledConnection();
			rowSet2 = new JdbcRowSetImpl(connection);			
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand(haalBestellingString);
			rowSet.setInt(1, bestelling_id);
			rowSet.execute();

			int i = 0;
			while (rowSet.next()) {
				Artikel artikel = aLijst.getArtikelWithArtikelId(rowSet.getInt(1));
				
				String haalAantal = String.format("SELECT artikel%d_aantal FROM bestelling WHERE bestelling_id=?", i + 1);
				rowSet2.setCommand(haalAantal);
				rowSet2.setInt(1, bestelling_id);
				rowSet2.execute();
				while (rowSet2.next()){
					artikel.setAantal(rowSet2.getInt(1));
				}
				bestelling.voegArtikelToeAanBestelling(artikel);
				i++;
			}
			System.out.println(bestelling.toString());

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
		}
		return bestelling;
	}
	*/
	
	@Override
	public Bestelling getBestelling(int bestelling_id){
		connection = DatabaseConnection.getPooledConnection();
		ArtikelLijst aLijst = new ArtikelLijst();
		Bestelling bestelling = null;
		try {
			RowSet bestelData = new JdbcRowSetImpl(connection);
			bestelData.setCommand(
					"SELECT * FROM Bestelling " +
					"INNER JOIN bestelling_has_artikel " +
					"ON Bestelling.bestelling_id = bestelling_has_artikel.bestelling_id " +
					"WHERE bestelling_id = ?");
			bestelData.setInt(1, bestelling_id);
			bestelData.execute();
			
			bestelling = new Bestelling();
			while (bestelData.next()){
				bestelling.setKlant_id(bestelData.getInt(2));
				if (bestelData.getInt(4) > 0){
					bestelling.voegArtikelToeAanBestelling
							(aLijst.getArtikelWithArtikelId(bestelData.getInt(4)), bestelData.getInt(5));
				}
			}
			bestelData.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return bestelling;
	}
	
	@Override
	public ArrayList<Bestelling> haalBestellijst(){
		connection = DatabaseConnection.getPooledConnection();
		RowSet rowSet = null;
		ArrayList<Bestelling> bestellijst = new ArrayList();
		try {
			bestellijst = new ArrayList<>();
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
					"DELETE FROM Bestelling " +
					"INNER JOIN bestelling_has_artikel" + 
					"WHERE bestelling_id = ?");
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
	public void updateBestelling(int bestelling_id, Bestelling bestelling) {
		PreparedStatement update = null;
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			connection = DatabaseConnection.getPooledConnection();
			
			PreparedStatement deleteArtikelenOud = connection.prepareStatement(
					"DELETE * FROM bestelling_has_artikel " +
					"WHERE bestelling_id = ?");
			deleteArtikelenOud.setInt(1, bestelling_id);
			deleteArtikelenOud.executeUpdate();
			deleteArtikelenOud.close();
			
			for (Map.Entry<Artikel, Integer> entrySet: artikelen.entrySet()){
				update = connection.prepareStatement(
						"INSERT INTO bestelling_has_artikel " +
						"(bestelling_id, artikel_id, artikel_aantal) " +
						"VALUES (?, ?, ?)");
				update.setInt(1, bestelling_id);
				update.setInt(2, entrySet.getKey().getId());
				update.setInt(3, entrySet.getValue());
				update.executeUpdate();
				update.close();
			}
			System.out.println("Bestelling " + bestelling_id + " is veranderd!");
			
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
			haalKlant_id = connection.prepareStatement("SELECT klant_id FROM Bestelling WHERE bestelling_id = ?");
			haalKlant_id.setInt(1, bestelling_id);
			ResultSet rs = haalKlant_id.executeQuery();

			if (rs.next()) {
				klant_id = rs.getInt(1);
			}
			haalKlant_id.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return klant_id;
	}
	
	public ArrayList<Bestelling> getBestellijstByKlant(int klant_id){
		connection = DatabaseConnection.getPooledConnection();
		
		ArrayList<Bestelling> bestellijst = new ArrayList();
		
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
	
	public void verwijderTabel() throws SQLException {
		connection = DatabaseConnection.getPooledConnection();

		Statement dropTable = connection.createStatement();
		dropTable.executeUpdate("DROP TABLE Bestelling");

		System.out.println("table Bestelling dropped!");
	}
	
	public void close(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed!");
	}

}
