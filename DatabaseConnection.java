import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
	static String URL;
	static String PW;
	static String USERNAME;
	
public static Connection getPooledConnection(){
		
		Scanner input = new Scanner(System.in);
		System.out.println("Wie bent u? \n1. Tim \n2. Maurice \n3. Sander \n ");
		int user = input.nextInt();
		switch (user) {
		case 1:	URL = "jdbc:mysql://localhost:3306/workshop";
				PW = "tiger";
				USERNAME = "scott";
				break;
		case 2: URL = "jdbc:mysql://localhost:3306/adresboek";
				PW = "komt_ie";
				USERNAME = "root";
				break;
		case 3: URL = "jdbc:mysql://localhost:3306/workshop";
				PW = "FrIkandel";
				USERNAME = "sandermegens";
				break;
		}
		
		Connection connection = null;
		
		boolean invalidInput = true;
		boolean invalidInput2 = true;
		while (invalidInput){
		System.out.println("Wilt u een connectietype kiezen? \n 1. Ja \n 2. Nee \n");
		int userInput = input.nextInt();
		if (userInput == 1){
			invalidInput = false;
			while (invalidInput2){
				System.out.println("Wilt u connectie via HikariCP of via c3p0? \n 1. HikariCP \n 2. c3p0 \n");
				userInput = input.nextInt();
			if (userInput == 1){
				invalidInput2 = false;
				return getHikariConnection();
			}
			else if (userInput == 2){
				invalidInput2 = false;
				return getC3p0Connection();
			}
			else 
				System.out.println("Verkeerde opgave, probeer opnieuw");
			}
		}
		else if (userInput == 2){
			invalidInput = false;	
			return getHikariConnection();
		}
		else
			System.out.println("Verkeerde opgave probeer opnieuw");	
		}
		input.close();
		return connection;
	}
	
	public static Connection getHikariConnection() {

		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(3);
		config.setInitializationFailFast(true);
		config.setJdbcUrl(URL);
		config.setUsername(USERNAME);
		config.setPassword(PW);
		config.addDataSourceProperty("useSSL", "false");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		HikariDataSource dataSource = new HikariDataSource(config);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			
		}
		return connection;
	}

	public static Connection getC3p0Connection(){
		
		Connection connection = null;
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		try{
		cpds.setDriverClass( "com.mysql.jdbc.Driver" );
		cpds.setJdbcUrl(URL+"?useSSL=false"); 
		cpds.setUser(USERNAME); 
		cpds.setPassword(PW);
		cpds.setMinPoolSize(1); 
		cpds.setAcquireIncrement(1); 
		cpds.setMaxPoolSize(3);
		
		connection = cpds.getConnection();
	}
	catch (Exception ex){
		ex.printStackTrace();
	}
	finally{
		
	}
	return connection;		
	}

	public static Connection getSingleConnection() {
		final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		final String USERNAME = "sandermegens";
		final String pw = "FrIkandel";
		final String URL = "jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false";
		Connection connection = null;

		try {
			Class.forName(DRIVER_CLASS);
			if (connection == null)
				connection = DriverManager.getConnection(URL, USERNAME, pw);
			System.out.println("connection made");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return connection;

	}

}