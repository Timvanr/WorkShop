import java.sql.*;

import com.sun.rowset.*;
import java.util.ArrayList;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;


public class Bestellijst implements BestellingenDAO {
	private static Connection connection;
	
	public Bestellijst(){
		try {
			getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.print("Driver loaded... ");
			
				connection = DriverManager.getConnection("jdbc:mysql://localhost/Adresboek", "root", "komt_ie");
				System.out.println("Database connected!");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
				
		return connection;
	}
	
	@Override
	public void maakTabel() throws SQLException {
		getConnection();
		
		Statement createTable = connection.createStatement();
			
		createTable.executeUpdate("CREATE TABLE Bestelling (" +
				"bestelling_id INT AUTO_INCREMENT, " +
				"klant_id INT NOT NULL, " +
				"artikel1_id INT, " +
				"artikel1_naam VARCHAR(255), " +
				"artikel1_aantal INT, " +
				"artikel1_prijs FLOAT, " +
				"artikel2_id INT, " +
				"artikel2_naam VARCHAR(255), " +
				"artikel2_aantal INT, " +
				"artikel2_prijs FLOAT, " +
				"artikel3_id INT, " +
				"artikel3_naam VARCHAR(255), " +
				"artikel3_aantal INT, " +
				"artikel3_prijs FLOAT, " +
				"PRIMARY KEY (bestelling_id), " +
				"FOREIGN KEY (klant_id) REFERENCES Klant(klant_id))");
		
		System.out.println("table Bestellingen created!");
	}

	@Override
	public void voegBestellingToe(Bestelling bestelling) throws SQLException {
		getConnection();
		
		ArrayList<Artikel> artikelen = bestelling.getArtikelen();
		
		String sql = "";
		String values = "";
		
		for (int i = 1; i <= Math.min(3, artikelen.size()); i++){
			sql += String.format(", artikel%d_id, artikel%d_naam, artikel%d_aantal, artikel%d_prijs", i, i, i, i);
			values += ", ?, ?, ?, ?";
		}
				
		PreparedStatement voegToe = connection.prepareStatement(
				String.format("INSERT INTO Bestelling (klant_id%s) VALUES (?%s)", sql, values), Statement.RETURN_GENERATED_KEYS);
		
		voegToe.setInt(1, bestelling.getKlant_id());
		
		for (int i = 0; i < Math.min(3, artikelen.size()); i++){
			voegToe.setInt(2 + i * 4, artikelen.get(i).getId());
			voegToe.setString(3 + i * 4, artikelen.get(i).getNaam());
			voegToe.setInt(4 + i * 4, artikelen.get(i).getAantal());
			voegToe.setDouble(5 + i * 4, artikelen.get(i).getPrijs());
		}	
		voegToe.executeUpdate();
		
		ResultSet rs = voegToe.getGeneratedKeys();
		
		if (rs.isBeforeFirst()){
			rs.next();
			bestelling.setId(rs.getInt(1));
		}
			
		System.out.println("Bestelling added!");
	}

	public static Bestelling haalBestelling(int id) throws SQLException {
		getConnection();
		
		PreparedStatement haalBestelling = connection.prepareStatement
				("SELECT * FROM Bestelling WHERE bestelling_id = ?");
		haalBestelling.setInt(1, id);
		ResultSet gegevens = haalBestelling.executeQuery();
		
		JdbcRowSet rowSet = new JdbcRowSetImpl(gegevens);
		
		Bestelling bestelling = new Bestelling(rowSet.getInt(2));
		
		for (int i = 0; i < 3; i++){
			if (rowSet.getInt(3 + i * 4) > 0){
				bestelling.voegArtikelToe(new Artikel(rowSet.getInt(3 + i * 4), rowSet.getString(4 + i * 4), rowSet.getInt(5 + i * 4), rowSet.getDouble(6 + i * 4)));
			}
		}
		rowSet.close();
		
		bestelling.toString();//misschien beter niet hier?!?
		
		return bestelling;
	}
	
	@Override
	public ArrayList<Bestelling> haalBestellijst() throws SQLException{
		getConnection();
		
		ArrayList<Bestelling> bestellijst = new ArrayList<>();
				
		RowSet rowSet = new JdbcRowSetImpl(connection);
		rowSet.setCommand("SELECT * FROM Bestelling");
		rowSet.execute();
		
		while (rowSet.next()){
			bestellijst.add(haalBestelling(rowSet.getInt(1)));
		}
		rowSet.close();
		
		return bestellijst;
	}
	
	@Override
	public void verwijderBestelling(int id) throws SQLException {
		getConnection();
		
		PreparedStatement verwijder = connection.prepareStatement("DELETE FROM Bestelling WHERE bestelling_id = ?");
		verwijder.setInt(1, id);
		verwijder.executeUpdate();
		
		System.out.println("Bestelling " + id + " is verwijderd!");
	}
	
	@Override
	public void updateBestelling(Bestelling bestelling) throws SQLException{
		getConnection();
		
		ArrayList<Artikel> artikelen = bestelling.getArtikelen();
		
		String sqlupdate = "UPDATE Bestelling SET ";
		for (int i = 0; i < Math.min(3, artikelen.size()); i++){
			sqlupdate += String.format
					("artikel%d_id = ?, artikel%d_naam = ?, artikel%d_aantal = ?, artikel%d_prijs = ? ", i +1, i +1, i +1, i +1);
		}
		sqlupdate += "WHERE bestelling_id = " + bestelling.getId();
		
		PreparedStatement update = connection.prepareStatement(sqlupdate);
		
		for (int i = 0; i < Math.min(3, artikelen.size()); i++){
			update.setInt(1 + i * 4, artikelen.get(i).getId());
			update.setString(2 + i * 4, artikelen.get(i).getNaam());
			update.setInt(3 + i * 4, artikelen.get(i).getAantal());
			update.setDouble(4 + i * 4, artikelen.get(i).getPrijs());
		}
		update.execute();
		
		System.out.println("Bestelling " + bestelling.getId() + " is veranderd!");
	}
	
	@Override
	public int haalKlant_id(int bestelling_id) throws SQLException {
		getConnection();
		
		PreparedStatement haalKlant_id = connection.prepareStatement
				("SELECT klant_id FROM Bestelling WHERE bestelling_id = ?");
		haalKlant_id.setInt(1, bestelling_id);
		ResultSet rs = haalKlant_id.executeQuery();
		
		int klant_id = 0;
		if (rs.next()){
			klant_id += rs.getInt(1);
		}
		
		return klant_id;
	}

	@Override
	public void verwijderTabel() throws SQLException {
		getConnection();
		
		Statement dropTable = connection.createStatement();
		dropTable.executeUpdate("DROP TABLE Bestelling");
		
		System.out.println("table Bestelling dropped!");
	}
	
	@Override
	public void close() throws SQLException {
		connection.close();
		System.out.println("Connection closed!");
	}
}
