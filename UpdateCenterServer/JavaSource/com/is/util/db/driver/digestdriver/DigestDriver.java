package com.is.util.db.driver.digestdriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 
 * DigestDriver - wrapper for PostgreSQL JDBC Driver. Implements all methods defined in JDBC API.
 * Every method converts input and output parameters, if necessary, to make them PostgreSQL compatible, then delegates 
 * execution to appropriate method in Postgre's JDBC driver. 
 *
 */
public class DigestDriver implements Driver {

	//private static final Category LOG = Category.getInstance(ReportInformixDriver.class);
	protected static final String 	JDBC_URL_PREFIX		= "digest:";
	protected static final String 	JDBC_DEBUG_URL_PREFIX= "digestdebug:";
	protected static final int 		DRIVER_MAJOR_VERSION= 0;
	protected static final int 		DRIVER_MINOR_VERSION= 3;
	protected static final boolean	DRIVER_JDBC_COMPIANT= true;
	protected static final String 	WRAPPED_DRIVER_CLASS_PEOPERTY= "WRAPPED_DRIVER";
	
	static {
		try {
			java.sql.DriverManager.registerDriver(new DigestDriver());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public DigestDriver(){
	}
	
	public Connection connect(String url, java.util.Properties info) throws SQLException {
		
		try {
			boolean debugMode = url.indexOf(JDBC_DEBUG_URL_PREFIX)>=0;
			url = url.replaceAll(JDBC_URL_PREFIX,"").replaceAll(JDBC_DEBUG_URL_PREFIX,"");
			
			Class.forName("org.postgresql.Driver");
			Driver driver = DriverManager.getDriver(url);		
			
			Connection testConnection = debugMode? driver.connect(url, info) : null;
			DigestConnection conn = new DigestConnection(driver.connect(url, info), testConnection, debugMode);
			
			return conn;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("You must set wrapped driver class name in connection property "+WRAPPED_DRIVER_CLASS_PEOPERTY+"!",e);
		}
	}

	public boolean acceptsURL(String url) throws SQLException {
		String s = url.toString();
		return (s.indexOf(JDBC_URL_PREFIX)>=0 || s.indexOf(JDBC_DEBUG_URL_PREFIX)>=0);
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, java.util.Properties info) throws SQLException {
		return new DriverPropertyInfo[]{};
	}

	public int getMajorVersion() {
		return DRIVER_MAJOR_VERSION;	
	}

	public int getMinorVersion() {
		return DRIVER_MAJOR_VERSION;
	}


	public boolean jdbcCompliant() {
		return DRIVER_JDBC_COMPIANT;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
