package com.is.util.db.driver.digestdriver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;



public class DigestConnection implements Connection {

	private Connection mConn;
	private Connection mTestConn;
	private boolean    mDebugMode;
	
	public DigestConnection(Connection aConn, Connection aTestConn, boolean aDebugMode) throws SQLException {
		mConn = aConn;
		mTestConn = aTestConn;
		mDebugMode = aDebugMode;
	}
	
	public void clearWarnings() throws SQLException {
		mConn.clearWarnings();
	}

	public void close() throws SQLException {
		mConn.close();
	}

	public void commit() throws SQLException {
		mConn.commit();
	}

	public Statement createStatement() throws SQLException {
		return new DigestStatement(mConn.createStatement(), this, mDebugMode);
	}

	public boolean getAutoCommit() throws SQLException {
		return mConn.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return mConn.getCatalog();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return mConn.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return mConn.getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException {
		return mConn.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return mConn.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return mConn.isReadOnly();
	}

	public String nativeSQL(String arg0) throws SQLException {
		return mConn.nativeSQL(arg0);
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		return new DigestCallableStatement(arg0, mConn.prepareCall(arg0), this, arg0, mTestConn, mDebugMode);
	}

	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0), this, arg0, mTestConn, mDebugMode);
	}
	
	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0, arg1), this, arg0, mTestConn, mDebugMode);
	}
	
	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0, arg1), this, arg0, mTestConn, mDebugMode);
	}
	
	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0, arg1), this, arg0, mTestConn, mDebugMode);
	}
	
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0, arg1, arg2), this, arg0, mTestConn, mDebugMode);
	}
	
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return new DigestPreparedStatement(mConn.prepareStatement(arg0, arg1, arg2, arg3), this, arg0, mTestConn, mDebugMode);
	}

	public void rollback() throws SQLException {
		mConn.rollback();
	}

	public void setAutoCommit(boolean arg0) throws SQLException {
		mConn.setAutoCommit(arg0);
	}

	public void setCatalog(String arg0) throws SQLException {
		mConn.setCatalog(arg0);
	}

	public void setReadOnly(boolean arg0) throws SQLException {
		mConn.setReadOnly(arg0);
	}

	public void setTransactionIsolation(int arg0) throws SQLException {
		mConn.setTransactionIsolation(arg0);
	}
	
	public Savepoint setSavepoint(String arg0) throws SQLException {
		return mConn.setSavepoint(arg0);
	}
   
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    	return new DigestStatement(mConn.createStatement(resultSetType, resultSetConcurrency), this, mDebugMode);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, 
				  int resultSetConcurrency) throws SQLException {
    	return new DigestCallableStatement(sql, mConn.prepareCall(sql, resultSetType, resultSetConcurrency), this, sql, mTestConn, mDebugMode);
    }
   
    public void setHoldability(int holdability) throws SQLException {
    	mConn.setHoldability(holdability);
    }

    
    public int getHoldability() throws SQLException {
    	return mConn.getHoldability();
    }

    
    public Savepoint setSavepoint() throws SQLException {
    	return mConn.setSavepoint();
    }

    
    public void rollback(Savepoint savepoint) throws SQLException {
    	mConn.rollback(savepoint);
    }

   
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    	mConn.releaseSavepoint(savepoint);
    }

    
    public Statement createStatement(int resultSetType, int resultSetConcurrency, 
			      int resultSetHoldability) throws SQLException {
    	return new DigestStatement(mConn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), this, mDebugMode);
    }
    
    public CallableStatement prepareCall(String sql, int resultSetType, 
				  int resultSetConcurrency, 
				  int resultSetHoldability) throws SQLException {
    	return new DigestCallableStatement(sql, mConn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), this, sql, mTestConn, mDebugMode);
    }

	
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return mConn.isWrapperFor(arg0);
	}

	
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return mConn.unwrap(arg0);
	}

	
	public Clob createClob() throws SQLException {
		return mConn.createClob();
	}

	
	public Blob createBlob() throws SQLException {
		return mConn.createBlob();
	}

	
	public NClob createNClob() throws SQLException {
		return mConn.createNClob();
	}

	
	public SQLXML createSQLXML() throws SQLException {
		return mConn.createSQLXML();
	}

	
	public boolean isValid(int i) throws SQLException {
		return mConn.isValid(i);
	}

	
	public void setClientInfo(String s, String s1)
			throws SQLClientInfoException {
		mConn.setClientInfo(s, s1);
	}

	
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		mConn.setClientInfo(properties);
	}

	
	public String getClientInfo(String s) throws SQLException {
		return mConn.getClientInfo(s);
	}

	
	public Properties getClientInfo() throws SQLException {
		return mConn.getClientInfo();
	}

	
	public Array createArrayOf(String s, Object[] aobj) throws SQLException {
		return mConn.createArrayOf(s, aobj);
	}

	
	public Struct createStruct(String s, Object[] aobj) throws SQLException {
		return mConn.createStruct(s, aobj);
	}

	
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		mConn.setTypeMap(arg0);
	}

	
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return mConn.getTypeMap();
	}
	
	public Connection getWrappedConnection(){
		return mConn;
	}

	public Connection getTestConnection() {
		return mTestConn;
	}
	
	public boolean getIsDebugMode() {
		return mDebugMode;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
