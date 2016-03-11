import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class BestellingDAO {

	final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	final static String USERNAME = "sandermegens";
	final static String pw = "FrIkandel";
	final static String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";

	
	public static void readFromBestellingId(int bestelling_id) throws SQLException{
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
	
public static void updateBestelling(int bestelling_id) throws IOException, SQLException{
		
		Connection connection = null;
		PreparedStatement updateBestelling= null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		connection = DatabaseConnection.getConnection();
		
		for (int i = 1; i <= 3; i++){
		String updateBestellingQuery = String.format("UPDATE bestelling SET artikel%d_naam=?, artikel%d_prijs=?, artikel%d_aantal=?, artikel%d_id=? WHERE bestelling_id=?", i, i, i, i);
				
		try {
			System.out.println("enter new artikel naam");
			String artikelNaam = br.readLine();
			System.out.println("enter new prijs");
			String artikelPrijsStr = br.readLine();
			int artikelPrijs = Integer.parseInt(artikelPrijsStr);
			System.out.println("enter new aantal");
			String artikelAantalStr = br.readLine();
			int artikelAantal = Integer.parseInt(artikelAantalStr);
			System.out.println("enter new artikel id");
			String artikelIdStr = br.readLine();
			int artikelId = Integer.parseInt(artikelIdStr);
			
		
			updateBestelling = connection.prepareStatement(updateBestellingQuery);
			updateBestelling.setString(1, artikelNaam);
			updateBestelling.setInt(2, artikelPrijs);
			updateBestelling.setInt(3, artikelAantal);
			updateBestelling.setInt(4, artikelId);
			updateBestelling.setInt(5, bestelling_id);
			
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
	public static void deleteBestellingFromId(int bestelling_id) throws SQLException{
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
