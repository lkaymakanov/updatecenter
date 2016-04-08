package com.is.util.db.driver.digestdriver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.is.util.db.driver.digestdriver.Logger.Log;

public class DigestPreparedStatement extends DigestStatement implements PreparedStatement {
	
	protected PreparedStatement mPStmt;
	protected String mQuery;
	protected Connection mTestConnection;
	
	public DigestPreparedStatement(PreparedStatement aPStmt, DigestConnection aParentConnection, String aQuery, Connection aTestConnection, boolean aDebugMode) {
		super(aPStmt, aParentConnection, aDebugMode);
		mPStmt = aPStmt;
		mQuery = aQuery;
		mTestConnection = aTestConnection;
	}
	
	public void clearParameters() throws SQLException {
		mPStmt.clearParameters();
	}

	public boolean execute() throws SQLException {
		return mPStmt.execute();
	}

	public ResultSet executeQuery() throws SQLException {
		return new DigestResultSet(mPStmt.executeQuery(), this, mDebugMode);
	}

	public int executeUpdate() throws SQLException {
		return mPStmt.executeUpdate();
	}

	public void setAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		mPStmt.setAsciiStream(arg0, arg1, arg2);
	}

	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setBigDecimal, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		mPStmt.setBinaryStream(arg0, arg1, arg2);
	}

	public void setBoolean(int arg0, boolean arg1) throws SQLException {
//		mPStmt.setBoolean(arg0, arg1);
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setBoolean, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setByte(int arg0, byte arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setByte, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		mPStmt.setBytes(arg0, arg1);
	}

	public void setDate(int arg0, Date arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setDate, mParentConnection.getTestConnection(), mDebugMode);
	}
	
	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setDate, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setDouble(int arg0, double arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setDouble, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setFloat(int arg0, float arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setFloat, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setInt(int arg0, int arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setInt, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setLong(int arg0, long arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setLong, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setNull(int arg0, int arg1) throws SQLException {
		mPStmt.setNull(arg0, arg1);
	}

	public void setObject(int arg0, Object arg1) throws SQLException {
//		if (arg1!=null) {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
			InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			mPStmt.setObject(arg0, arg1);
//		}
	}

	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
//		if (arg1!=null) {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
			InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			mPStmt.setObject(arg0, arg1, arg2);
//		}
	}

	public void setObject(int arg0, Object arg1, int arg2, int arg3) throws SQLException {
//		if (arg1!=null) {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
			InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			mPStmt.setObject(arg0, arg1, arg2, arg3);
//		}
	}

	public void setShort(int arg0, short arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setShort, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setString(int arg0, String arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setString, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setTime(int arg0, Time arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setTime, mParentConnection.getTestConnection(), mDebugMode);
	}
	
	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setTime, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setTimestamp, mParentConnection.getTestConnection(), mDebugMode);
	}
	
	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2) throws SQLException {
		InParams.setPreparedParameter(mPStmt, arg0, arg1, mQuery, SettersEnum.setTimestamp, mParentConnection.getTestConnection(), mDebugMode);
	}

	public void setUnicodeStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		mPStmt.setUnicodeStream(arg0, arg1, arg2);
	}
	
	public ResultSetMetaData getMetaData() throws SQLException{
		return mPStmt.getMetaData();
	}
	
	public void setRef(int arg0,Ref arg1) throws SQLException {
		mPStmt.setRef(arg0, arg1);
	}
	
	public void addBatch() throws SQLException {
		mPStmt.addBatch();
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		mPStmt.setCharacterStream(parameterIndex, reader, length);
	}

	public void setBlob (int i, Blob x) throws SQLException {
		mPStmt.setBlob (i, x);
	}

	public void setClob (int i, Clob x) throws SQLException {
		mPStmt.setClob (i, x);
	}

	public void setArray (int i, Array x) throws SQLException {
		mPStmt.setArray (i, x);
	}

	public void setNull (int paramIndex, int sqlType, String typeName) throws SQLException {
		mPStmt.setNull (paramIndex, sqlType, typeName);
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		mPStmt.setURL(parameterIndex, x);
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return mPStmt.getParameterMetaData();
	}


	
	public void setRowId(int i, RowId rowid) throws SQLException {
		(mPStmt).setRowId(i, rowid);
	}

	
	public void setNString(int i, String s) throws SQLException {
		if (mDebugMode) {
			Log.convErr(new Exception("<PreparedStatement>.setNString should not be used! PostgreSQL does not supports it."), ""+i);
		}
		InParams.setPreparedParameter(mPStmt, i, s, mQuery, SettersEnum.setString, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setNCharacterStream(int i, Reader reader, long l)
			throws SQLException {
		(mPStmt).setNCharacterStream(i, reader, l);
	}

	
	public void setNClob(int i, NClob nclob) throws SQLException {
		(mPStmt).setNClob(i, nclob);
	}

	
	public void setClob(int i, Reader reader, long l) throws SQLException {
		mPStmt.setClob(i, reader, l);	
	}

	
	public void setBlob(int i, InputStream inputstream, long l)
			throws SQLException {
		mPStmt.setBlob(i, inputstream, l);
	}

	
	public void setNClob(int i, Reader reader, long l) throws SQLException {
		mPStmt.setNClob(i, reader, l);
	}

	
	public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
		(mPStmt).setSQLXML(i, sqlxml);
	}

	
	public void setAsciiStream(int i, InputStream inputstream, long l)
			throws SQLException {
		mPStmt.setAsciiStream(i, inputstream, l);
	}

	
	public void setBinaryStream(int i, InputStream inputstream, long l)
			throws SQLException {
		mPStmt.setBinaryStream(i, inputstream, l);
	}

	
	public void setCharacterStream(int i, Reader reader, long l)
			throws SQLException {
		mPStmt.setCharacterStream(i, reader, l);
	}

	
	public void setAsciiStream(int i, InputStream inputstream)
			throws SQLException {
		mPStmt.setAsciiStream(i, inputstream);
	}

	
	public void setBinaryStream(int i, InputStream inputstream)
			throws SQLException {
		mPStmt.setBinaryStream(i, inputstream);
	}

	
	public void setCharacterStream(int i, Reader reader) throws SQLException {
		mPStmt.setCharacterStream(i, reader);
	}

	
	public void setNCharacterStream(int i, Reader reader) throws SQLException {
		// Oracle's setNCharacterStream(int i, Reader reader) hangs on the tests! Because of this Postgre's is not tested!
		(mPStmt).setNCharacterStream(i, reader);
	}

	
	public void setClob(int i, Reader reader) throws SQLException {
		mPStmt.setClob(i, reader);
	}

	
	public void setBlob(int i, InputStream inputstream) throws SQLException {
		mPStmt.setBlob(i, inputstream);
	}

	
	public void setNClob(int i, Reader reader) throws SQLException {
		mPStmt.setNClob(i, reader);
	}
	
	public PreparedStatement getWrappedPreparedStatement(){
		return mPStmt;
	}
	
}
