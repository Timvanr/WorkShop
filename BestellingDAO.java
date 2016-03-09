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
		
		String updateBestellingQuery = "UPDATE bestelling SET artikel1_naam=?, artikel1_prijs=?, artikel1_aantal=?, artikel1_id=? "
				//+ "artikel2_naam=?, artikel2_prijs=?, artikel2_aantal=?, artikel2_id=?,"
				+ /*"artikel3_naam=?, artikel3_prijs=?, artikel3_aantal=?, artikel3_id=?,*/"WHERE bestelling_id=?;";
		Connection connection = null;
		PreparedStatement updateAdres = null;
		String newArtikel1_naam = null;
		String newArtikel1_prijsstring = null;
		String newArtikel1_aantalstring = null;
		String newArtikel1_idstring = null;
		/*String newArtikel2_naam = null;
		String newArtikel2_prijsstring = null;
		String newArtikel2_aantalstring = null;
		String newArtikel2_idstring = null;
		String newArtikel3_naam = null;
		String newArtikel3_prijsstring = null;
		String newArtikel3_aantalstring = null;
		String newArtikel3_idstring = null;
		*/
		int newArtikel1_prijs = 0;
		int newArtikel1_aantal = 0;
		int newArtikel1_id = 0;
		/*int newArtikel2_prijs = 0;		DEZE EVEN LATEN ZITTEN MOET TOCH VERANDERD WORDEN LATER
		int newArtikel2_aantal = 0;
		int newArtikel2_id = 0;
		int newArtikel3_prijs = 0;
		int newArtikel3_aantal = 0;
		int newArtikel3_id = 0;
		*/
		try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
			
			System.out.println("enter new naam voor artikel1");
			newArtikel1_naam = input.readLine();
			System.out.println("enter new prijs voor artikel1");
			newArtikel1_prijsstring = input.readLine();
			newArtikel1_prijs = Integer.parseInt(newArtikel1_prijsstring);
			System.out.println("enter new aantal voor artikel1");
			newArtikel1_aantalstring = input.readLine();
			newArtikel1_aantal = Integer.parseInt(newArtikel1_aantalstring);
			System.out.println("enter new id voor artikel1");
			newArtikel1_idstring = input.readLine();
			newArtikel1_id = Integer.parseInt(newArtikel1_idstring);
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		try{
			connection = DatabaseConnection.getConnection();
			updateAdres = connection.prepareStatement(updateBestellingQuery);
			updateAdres.setString(1, newArtikel1_naam);
			updateAdres.setInt(2, newArtikel1_prijs);
			updateAdres.setInt(3, newArtikel1_aantal);
			updateAdres.setInt(4, newArtikel1_id);
			updateAdres.setInt(5, bestelling_id);
			
			updateAdres.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally {
			updateAdres.close();
			connection.close();
		}
		
		
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
