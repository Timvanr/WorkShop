import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import javax.sql.RowSet;
import com.mysql.jdbc.Statement;
import com.sun.rowset.JdbcRowSetImpl;

public class BestellingDAOFireBird implements BestellingDAO{
	Connection connection = null;
	
	public BestellingDAOFireBird(){
		
	}
	
	public static Connection getConnection(){
		final String DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver";
		final String USERNAME = "SYSDBA";
		final String pw = "MasterKey";
		final String URL = "jdbc:firebirdsql://localhost:3050/D:/HOST.gdb";
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
	
	@Override
	public void voegBestellingToe(Bestelling bestelling) {

		String insertIdsString = "INSERT INTO bestelling_has_artikel (bestelling_id, artikel_id) values (?,?);";
		PreparedStatement insertIds = null;
		String sql = "";
		String values = "";
		
		HashMap<Artikel, Integer> artikelen = bestelling.getArtikelen();
		try {
			Connection connection = getConnection();
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
			try {
				insertIds.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

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
				bestelling.voegArtikelToeAanBestelling(artikel);*/
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
		Connection connection = getConnection();
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
	
	public ArrayList<Bestelling> haalBestellijst() {
		Connection connection = getConnection();
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
			try {
				rowSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bestellijst;
	}

	public void verwijderBestelling(int bestelling_id) {
		PreparedStatement verwijder = null;
		try {
			Connection connection = getConnection();
			verwijder = connection.prepareStatement(
					"DELETE FROM Bestelling INNER JOIN bestelling_has_artikel" + "WHERE bestelling_id = ?");
			verwijder.setInt(1, bestelling_id);
			verwijder.executeUpdate();

			System.out.println("Bestelling " + bestelling_id + " is verwijderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				verwijder.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateBestelling(Bestelling bestelling) throws SQLException {
		PreparedStatement insertId = null;
		PreparedStatement update = null;
		try {
			Connection connection = getConnection();
			String updateIdsString = "UPDATE bestelling_has_artikel SET artikel_id " + "WHERE bestelling_id="
					+ bestelling.getBestelling_id();
			insertId = connection.prepareStatement(updateIdsString);
/*
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
			}*/
			System.out.println("Bestelling " + bestelling.getBestelling_id() + " is veranderd!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			insertId.close();
			update.close();
		}
	}

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
	
	public void close() {
		try {
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed!");
		
	}

	@Override
	public void updateBestelling(int bestelling_id, Bestelling bestelling) {
		// TODO Auto-generated method stub
		
	}

}
