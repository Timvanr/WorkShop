import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;

import com.sun.rowset.*;
import java.util.ArrayList;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;


public class Bestellijst implements BestellingenDAO {
	
	private final static String URL = "jdbc:mysql://localhost/Adresboek";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "komt_ie";
	
	private static Connection connection;
	
	public Bestellijst() {
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
				
		
	@Override//Het was volgens mij de bedoeling dat iedere keer als er ingelogd wordt er een nieuwe tabel wordt aangemaakt
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
	// DIT NOG EVEN NAKIJKEN KLOPT NU NOG NIET... MOET WORDEN KLANT_ID EN BESTELLING
	public void voegBestellingToe(Klant klant, Bestelling bestelling) throws SQLException {
		String sql = "";
		String values = "";
		Connection connection = DatabaseConnection.getConnection();
		
		for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++){
			sql += String.format(", artikel%d_id, artikel%d_naam, artikel%d_aantal, artikel%d_prijs", i + 1, i + 1, i + 1, i + 1);
			values += ", ?, ?, ?, ?";
		}
				
		PreparedStatement voegToe = connection.prepareStatement(
				String.format("INSERT INTO Bestelling (klant_id%s) VALUES (?%s)", sql, values), Statement.RETURN_GENERATED_KEYS);
		
		voegToe.setInt(1, klant.getId());
		
		for (int i = 0; i < Math.min(3, bestelling.artikelen.size()); i++){
			voegToe.setInt(2 + i * 4, bestelling.artikelen.get(i).getId());
			voegToe.setString(3 + i * 4, bestelling.artikelen.get(i).getNaam());
			voegToe.setInt(4 + i * 4, bestelling.artikelen.get(i).getAantal());
			voegToe.setBigDecimal(5 + i * 4, bestelling.artikelen.get(i).getPrijs());
		}	
		voegToe.executeUpdate();
		
		ResultSet rs = voegToe.getGeneratedKeys();
		
		if (rs.isBeforeFirst()){
			rs.next();
			bestelling.setBestelling_id(rs.getInt(1));
		}
			
		System.out.println("Bestelling added!");
	}

	public static Bestelling haalBestelling(int id) throws SQLException {
		getConnection();
		Bestelling bestelling = new Bestelling();
		String haalBestelling = "SELECT artikel_id, artikel_naam, artikel_prijs, artikel_aantal FROM Bestelling WHERE bestelling_id = ?";
		
		RowSet rowSet = new JdbcRowSetImpl();
		rowSet.setUrl(URL);
		rowSet.setPassword(PASSWORD);
		rowSet.setUsername(USERNAME);
		rowSet.setCommand(haalBestelling);
		rowSet.setInt(1, id);
		
		rowSet.execute();	
		
		while (rowSet.next()){
			
			for (int i = 0; i < 3; i++){
					bestelling.voegArtikelToeAanBestelling(new Artikel(rowSet.getInt(i * 4 + 1), rowSet.getString(i * 4 + 2), rowSet.getBigDecimal(i * 4 + 3), rowSet.getInt(i * 4 + 4)));
			}
		}
		rowSet.close();
		
		return bestelling;
	}
	
	public void haalKlantEnBestelling(int bestelling_id) throws SQLException{
		RowSet rowSet = new JdbcRowSetImpl();
		String query = "Select * from klant INNER JOIN bestelling ON klant.klant_id=bestelling.klant_id where bestelling_id=?;";
		ResultSetMetaData rsMD = null;
		try{
			rowSet.setUrl(URL);
			rowSet.setPassword(PASSWORD);
			rowSet.setUsername(USERNAME);
			rowSet.setCommand(query);
			rowSet.setInt(1, bestelling_id);
			
			rowSet.execute();
			
			if (!rowSet.isBeforeFirst()){
				System.out.println("no results found for this bestelling_id");
				return;
			}
			else{
				rsMD = rowSet.getMetaData();
				for (int i = 1; i <= rsMD.getColumnCount(); i++){
					System.out.printf("%-12s\t", rsMD.getColumnLabel(i));
				}
				System.out.println();
				while(rowSet.next()){
					for (int i = 1; i <= rsMD.getColumnCount(); i++){
						System.out.printf("%-12s\t", rowSet.getObject(i));
						
						if (i % rsMD.getColumnCount() == 0){
							System.out.println();
						}
					}
				}
			}
		}
			finally{
				rowSet.close();
			}
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
		
		Connection connection = null;
		PreparedStatement updateBestelling= null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		connection = DatabaseConnection.getConnection();
		
		for (int i = 1; i <= Math.min(3, bestelling.artikelen.size()); i++){
			
			int bestellingId = bestelling.getBestelling_id();
			String updateBestellingQuery = String.format("UPDATE bestelling SET artikel%d_naam=?, artikel%d_prijs=?, artikel%d_aantal=?, artikel%d_id=? WHERE bestelling_id=?" , i, i, i, i);
				
		try {
			BigDecimal artikelPrijs = new BigDecimal(0);
			System.out.println("enter new artikel naam");
			String artikelNaam = br.readLine();
			bestelling.artikelen.get(i).setNaam(artikelNaam);
			System.out.println("enter new prijs");
			String artikelPrijsStr = br.readLine();
			artikelPrijs = artikelPrijs.add(new BigDecimal(artikelPrijsStr));
			bestelling.artikelen.get(i).setPrijs(artikelPrijs);
			System.out.println("enter new aantal");
			String artikelAantalStr = br.readLine();
			int artikelAantal = Integer.parseInt(artikelAantalStr);
			bestelling.artikelen.get(i).setAantal(artikelAantal);
			System.out.println("enter new artikel id");
			String artikelIdStr = br.readLine();
			int artikelId = Integer.parseInt(artikelIdStr);
			bestelling.artikelen.get(i).setId(artikelId);
			
			updateBestelling = connection.prepareStatement(updateBestellingQuery);
			updateBestelling.setString(1, artikelNaam);
			updateBestelling.setBigDecimal(2, artikelPrijs);
			updateBestelling.setInt(3, artikelAantal);
			updateBestelling.setInt(4, artikelId);
			updateBestelling.setInt(5, bestellingId);
			
			
			updateBestelling.executeUpdate();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		
	}
			updateBestelling.close();
			connection.close();
		
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
