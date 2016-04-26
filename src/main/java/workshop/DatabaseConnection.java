package workshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
	private static String DriverClass;
	private static String URL; // Hier je databasenaam en pad
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
		
		if (getConnectieKeuze() == 1){
			return getHikariConnection();
		}
		else if (getConnectieKeuze() == 2){
			return getC3p0Connection();
		}
				
		return null;
	}

	private static Connection getC3p0Connection(){
		try {
			return getC3p0DataSource().getConnection();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static Connection getHikariConnection(){
		try {
			return getHikariDataSource().getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HikariDataSource getHikariDataSource() {
		if (hikariDS == null){
				
			HikariConfig config = new HikariConfig();
			config.setMinimumIdle(1);
			config.setMaximumPoolSize(14);
			config.setInitializationFailFast(true);
			config.setDriverClassName(DriverClass);
			config.setJdbcUrl(URL);
			config.setUsername(USERNAME);
			config.setPassword(PW);
			config.addDataSourceProperty("useSSL", "false");
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			HikariDataSource hikariDS = new HikariDataSource(config);
			
			return hikariDS;
		}
		return null;
	}

	private static ComboPooledDataSource getC3p0DataSource(){
		if (c3p0DS == null){
				
			ComboPooledDataSource c3p0DS = new ComboPooledDataSource();
			
			try{
				c3p0DS.setDriverClass(DriverClass);
				c3p0DS.setJdbcUrl(URL +"?useSSL=false"); 
				c3p0DS.setUser(USERNAME); 
				c3p0DS.setPassword(PW);
				c3p0DS.setMinPoolSize(1); 
				c3p0DS.setAcquireIncrement(1); 
				c3p0DS.setMaxPoolSize(14);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return c3p0DS;
			
		}
		return null;
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
