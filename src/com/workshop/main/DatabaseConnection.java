package com.workshop.main;

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
	private static ComboPooledDataSource c3p0DS;
	
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
			if (c3p0DS == null){
				getC3p0DataSource();
			}
			return c3p0DS.getConnection();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static Connection getHikariConnection(){
		try {
			if (hikariDS == null){
				getHikariDataSource();
			}
			return hikariDS.getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void getHikariDataSource() {
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
			
			HikariDataSource hkrDS = new HikariDataSource(config);
			
			hikariDS = hkrDS;
			System.out.println("Nieuwe connectiepool");
		}
	}

	private static void getC3p0DataSource(){
		if (c3p0DS == null){
				
			ComboPooledDataSource cpDS = new ComboPooledDataSource();
			
			try{
				cpDS.setDriverClass(DriverClass);
				cpDS.setJdbcUrl(URL +"?useSSL=false"); 
				cpDS.setUser(USERNAME); 
				cpDS.setPassword(PW);
				cpDS.setMinPoolSize(1); 
				cpDS.setAcquireIncrement(1); 
				cpDS.setMaxPoolSize(14);
				cpDS.setMaxIdleTime(100);
				cpDS.setMaxIdleTimeExcessConnections(100);
				cpDS.setMaxStatements(90);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			c3p0DS = cpDS;
			System.out.println("Nieuwe connectiepool");
		}
	}
	
	static void close(){
		if (getConnectieKeuze() == 1){
			hikariDS.close();
			hikariDS = null;
			System.out.println("HikariCP verbinding verbroken.");
		}
		if (getConnectieKeuze() == 2){
			c3p0DS.close();
			c3p0DS = null;
			System.out.println("C3p0 verbinding verbroken.");
		}
		/* Deze select werkt niet goed.. Waarom weet ik niet.. race condition??
		else {
			System.out.println("Verbinding verbreken is mislukt.");
		}
		*/
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
