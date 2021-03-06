package workshop.dao.firebird;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import javax.sql.RowSet;
import com.mysql.jdbc.Statement;
import com.sun.rowset.JdbcRowSetImpl;
import workshop.model.Bestelling;
import workshop.model.Artikel;
import workshop.DatabaseConnection;

public class BestellingDAO extends workshop.dao.mysql.BestellingDAO{
	private static Connection connection;
	
	public BestellingDAO(){
		connection = DatabaseConnection.getPooledConnection();
	}
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;		
	}
	
	@Override
	public void voegBestellingToe(Bestelling bestelling) {
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			connection = DatabaseConnection.getPooledConnection();
			PreparedStatement voegToe = connection.prepareStatement(
					"INSERT INTO Bestelling (klant_id) VALUES (?) RETURNING bestelling_id ");
			voegToe.setInt(1, bestelling.getKlant_id());
			ResultSet rs = voegToe.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();
				bestelling.setBestelling_id(rs.getInt("bestelling_id"));
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
			//close();
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
			Connection connection = getConnection();
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
				/*while (rowSet2.next()){
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
		Bestelling bestelling = null;
		workshop.dao.ArtikelDAOInterface aLijst = new ArtikelDAO();
		RowSet bestelData = null;
		try{
			bestelData = new JdbcRowSetImpl(connection);
			bestelData.setCommand("SELECT * FROM Bestelling INNER JOIN bestelling_has_artikel ON bestelling.bestelling_id=bestelling_has_artikel.bestelling_id WHERE bestelling.bestelling_id=?");
			bestelData.setInt(1, bestelling_id);
			bestelData.execute();
			 
			while (bestelData.next()){
				bestelling = new Bestelling();
				bestelling.setKlant_id(bestelData.getInt("klant_id"));
				bestelling.setBestelling_id(bestelling_id);
				Artikel artikel = aLijst.getArtikelWithArtikelId(bestelData.getInt("artikel_id"));
				bestelling.voegArtikelToe(artikel, bestelData.getInt("artikel_aantal"));			
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				bestelData.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return bestelling;
	}
	
	@Override
	public Set<Bestelling> haalBestellijst() {
		Connection connection = getConnection();
		RowSet rowSet = null;
		Set<Bestelling> bestellijst = new LinkedHashSet();
		try {
			rowSet = new JdbcRowSetImpl(connection);
			rowSet.setCommand("SELECT * FROM Bestelling");
			rowSet.execute();

			while (rowSet.next()) {
				bestellijst.add(getBestelling(rowSet.getInt(1)));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rowSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bestellijst;
	}

	@Override
	public void verwijderBestelling(int bestelling_id){
		PreparedStatement verwijder = null;
		PreparedStatement verwijder2 = null;
		try {
			connection = DatabaseConnection.getPooledConnection();
			verwijder = connection.prepareStatement(
					"DELETE FROM Bestelling WHERE bestelling.bestelling_id=?");
			verwijder2 = connection.prepareStatement(
					"DELETE FROM Bestelling WHERE bestelling.bestelling_id=?");
			verwijder.setInt(1, bestelling_id);
			verwijder2.setInt(1, bestelling_id);
			verwijder.executeUpdate();
			verwijder2.executeUpdate();
			
			System.out.println("Bestelling " + bestelling_id + " is verwijderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			//close();
		}
	}

	@Override
	public int haalKlant_id(int bestelling_id) {
		PreparedStatement haalKlant_id = null;
		int klant_id = 0;
		try {
			Connection connection = getConnection();
			haalKlant_id = connection.prepareStatement("SELECT klant_id FROM Bestelling WHERE bestelling_id = ?");
			haalKlant_id.setInt(1, bestelling_id);
			ResultSet rs = haalKlant_id.executeQuery();

			if (rs.next()) {
				klant_id = rs.getInt(1);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				haalKlant_id.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klant_id;
	}

	/*@Override
	public void verwijderTabel() {
		Connection connection = getConnection();

		java.sql.Statement dropTable = connection.createStatement();
		dropTable.executeUpdate("DROP TABLE Bestelling");

		System.out.println("table Bestelling dropped!");
	}*/

}
