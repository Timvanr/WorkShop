
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
	private static String DriverClass;
	private static String URL;
	private static String PW;
	private static String USERNAME;
	private static int connectieKeuze;
	private static HikariDataSource hikariDS;
	private static Connection hikariConn;
	private static ComboPooledDataSource c3p0DS;
	private static Connection c3p0Conn;
	
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

	private static Connection getC3p0Connection(){
		try {
			if (c3p0DS == null){
				c3p0DS = getC3p0DataSource();
				
				return c3p0DS.getConnection();
				
			} else if (c3p0Conn == null || c3p0Conn.isClosed()){
				return c3p0DS.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static Connection getHikariConnection(){
		try {
			if (hikariDS == null){
				hikariDS = getHikariDataSource();
				
				return hikariDS.getConnection();
			
			} else if (hikariConn == null || hikariConn.isClosed()){
				return hikariDS.getConnection();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HikariDataSource getHikariDataSource() {
		
		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(3);
		config.setInitializationFailFast(true);
		config.setDriverClassName(DriverClass);
		config.setJdbcUrl("jdbc:mysql://localhost:3306/Adresboek");
		config.setUsername(USERNAME);
		config.setPassword(PW);
		config.addDataSourceProperty("useSSL", "false");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		HikariDataSource dataSource = new HikariDataSource(config);
		
		return dataSource;
	}

	private static ComboPooledDataSource getC3p0DataSource(){
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		try{
			cpds.setDriverClass(DriverClass);
			cpds.setJdbcUrl(URL +"?useSSL=false"); 
			cpds.setUser(USERNAME); 
			cpds.setPassword(PW);
			cpds.setMinPoolSize(1); 
			cpds.setAcquireIncrement(1); 
			cpds.setMaxPoolSize(3);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cpds;		
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
