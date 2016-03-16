import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;

public class BestellingDAO {

	final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	final static String USERNAME = "sandermegens";
	final static String pw = "FrIkandel";
	final static String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";
	
	
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

	
	public void readFromBestellingId(int bestelling_id) throws SQLException{
		RowSet rowSet = new JdbcRowSetImpl();
		String query = "Select * from klant INNER JOIN bestelling ON klant.klant_id=bestelling.klant_id where bestelling_id=?;";
		ResultSetMetaData rsMD = null;
		try{
			rowSet.setUrl(URL);
			rowSet.setPassword(pw);
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
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			rowSet.close();
		}
			
	}
	
	public void updateBestelling(Bestelling bestelling) throws IOException, SQLException{
		
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
	public void deleteBestellingFromId(int bestelling_id) throws SQLException{
		Connection connection = null;
		PreparedStatement deleteFromId = null;
		String query = "Delete FROM `bestelling` WHERE bestelling_id=?;";
		
		try{
			connection = DatabaseConnection.getConnection();
			deleteFromId = connection.prepareStatement(query);
			deleteFromId.setInt(1, bestelling_id);
			
			deleteFromId.executeUpdate();
			
			
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			connection.close();
			deleteFromId.close();
		}
	}	
	
}
