import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	
	/*protected boolean checkConnection(Connection connection){
		try{
			if (connection.isValid(1)){
		
			return false;
		}}
		catch (SQLException ex2){
			ex2.printStackTrace();
		}
		return true;
	}*/
	
	public static Connection getConnection(){
		final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		final String USERNAME = "sandermegens";
		final String pw = "FrIkandel";
		final String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";
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
}
