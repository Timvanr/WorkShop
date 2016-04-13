
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
	static String DriverClass;
	static String URL;
	static String PW;
	static String USERNAME;
	static int connectieKeuze;
	
	public static String getDriverClass() {
		return DriverClass;
	}

	public static String getURL() {
		return URL;
	}

	public static String getPW() {
		return PW;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static int getConnectieKeuze() {
		return connectieKeuze;
	}

	public static void setConnectieKeuze(int keuze){
		connectieKeuze = keuze;
	}
	
	public static void setDriverClass(String driverClass){
		DriverClass = driverClass;
	}
	
	public static void setURL(String url){
		URL = url;
	}
	
	public static void setPW(String pw){
		PW = pw;
	}
	
	public static void setUSERNAME(String userName){
		USERNAME = userName;
	}
	
public static Connection getPooledConnection(){
	
	Connection connection = null;
		
	if (getConnectieKeuze() == 1){
			return getHikariConnection();
	}
	else if (getConnectieKeuze() == 2){
			return getC3p0Connection();
	}
			
	return connection;
	
	}
	
	public static Connection getHikariConnection() {

		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(3);
		config.setInitializationFailFast(true);
		config.setDriverClassName(DriverClass);
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
		cpds.setDriverClass(DriverClass);
		cpds.setJdbcUrl(URL +"?useSSL=false"); 
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