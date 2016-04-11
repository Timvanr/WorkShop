
import java.sql.*;

import com.sun.rowset.*;
import java.util.*;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
// Maandag nog even naar kijken: getbestelling v.s. haalbestelling en tabel indeling.
public class Bestellijst implements BestellingenDAO {
	private static Connection connection;
	
	public Bestellijst() {
		connection = DatabaseConnection.getPooledConnection();
	}

	@Override
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
	public void voegBestellingToe(Bestelling bestelling) throws SQLException {

		String insertIdsString = "INSERT INTO bestelling_has_artikel (bestelling_id, artikel_id) values (?,?);";
		PreparedStatement insertIds = null;
		String sql = "";
		String values = "";
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			connection = DatabaseConnection.getPooledConnection();
			insertIds = connection.prepareStatement(insertIdsString);
			for (int i = 1; i <= artikelen.size(); i++) {
				sql += String.format(", artikel%d_id, artikel%d_aantal", i, i);
				values += ", ?, ?";
			}

			PreparedStatement voegToe = connection.prepareStatement(
					String.format("INSERT INTO Bestelling (klant_id%s) VALUES (?%s)", sql, values),
					Statement.RETURN_GENERATED_KEYS);

			voegToe.setInt(1, bestelling.getKlant_id());
			
			int i = 0;
			for (Map.Entry<Artikel, Integer> entry: artikelen.entrySet()) {
				voegToe.setInt(2 + i * 2, entry.getKey().getId());
				voegToe.setInt(3 + i * 2, entry.getValue());
				i++;
			}
			voegToe.executeUpdate();

			ResultSet rs = voegToe.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				bestelling.setBestelling_id(rs.getInt(1)); //toch?? deze for-loop hoeft niet toch??
				/*
				for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
					int bestelling_id = rs.getInt(1);
					insertIds.setInt(1, bestelling_id);
					insertIds.setInt(2, bestelling.artikelen.get(i).getId());
					insertIds.executeUpdate();
				}
				*/
			}

			System.out.println("Bestelling added!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			insertIds.close();
		}
	}

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
					artikel.setAantal(rowSet2.getInt(1)); // Moeten deze en die hieronder niet samen? (voegArtikelToeAanBestelling(artikel, rowSet2.getInt(1));
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

	public Bestelling getBestelling(int bestelling_id){
		connection = DatabaseConnection.getPooledConnection();
		ArtikelLijst aLijst = new ArtikelLijst();
		Bestelling bestelling = null;
		try {
			RowSet bestelData = new JdbcRowSetImpl(connection);
			bestelData.setCommand("SELECT * FROM Bestelling WHERE bestelling_id = ?");
			bestelData.setInt(1, bestelling_id);
			bestelData.execute();
			
			if (bestelData.next()){
				bestelling = new Bestelling();
				bestelling.setKlant_id(bestelData.getInt(1));
				for (int i = 0; i < 3; i++){
					if (bestelData.getInt(2 + i * 2) > 0){
						bestelling.voegArtikelToeAanBestelling
								(aLijst.getArtikelWithArtikelId(bestelData.getInt(2 + i * 2)), bestelData.getInt(3 + i * 2));
					}
				}
			} else {
				System.out.println("Bestelling not found!");
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
	public ArrayList<Bestelling> haalBestellijst() throws SQLException {
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

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			rowSet.close();
		}
		return bestellijst;
	}

	@Override
	public void verwijderBestelling(int bestelling_id) throws SQLException {
		PreparedStatement verwijder = null;
		try {
			connection = DatabaseConnection.getPooledConnection();
			verwijder = connection.prepareStatement(
					"DELETE FROM Bestelling INNER JOIN bestelling_has_artikel" + "WHERE bestelling_id = ?");
			verwijder.setInt(1, bestelling_id);
			verwijder.executeUpdate();

			System.out.println("Bestelling " + bestelling_id + " is verwijderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			verwijder.close();
		}
	}

	@Override
	public void updateBestelling(Bestelling bestelling) throws SQLException {
		PreparedStatement insertId = null;
		PreparedStatement update = null;
		try {
			connection = DatabaseConnection.getPooledConnection();
			String updateIdsString = "UPDATE bestelling_has_artikel SET artikel_id " + "WHERE bestelling_id="
					+ bestelling.getBestelling_id();
			insertId = connection.prepareStatement(updateIdsString);

			String sqlUpdate = "UPDATE Bestelling SET ";
			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				sqlUpdate += String.format("artikel%d_id = ?, artikel%d_aantal = ?,", i + 1, i + 1);
			}
			sqlUpdate += "WHERE bestelling_id = " + bestelling.getBestelling_id();

			update = connection.prepareStatement(sqlUpdate);

			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				update.setInt(1 + i * 2, bestelling.artikelen.get(i).getId());
				update.setInt(2 + i * 2, bestelling.artikelen.get(i).getAantal());
			}
			update.executeUpdate();

			for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++) {
				insertId.setInt(1, bestelling.artikelen.get(i).getId());
				insertId.executeUpdate();
			}
			System.out.println("Bestelling " + bestelling.getBestelling_id() + " is veranderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			insertId.close();
			update.close();
		}
	}

	@Override
	public int haalKlant_id(int bestelling_id) throws SQLException {
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

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			haalKlant_id.close();
		}
		return klant_id;
	}

	@Override
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
