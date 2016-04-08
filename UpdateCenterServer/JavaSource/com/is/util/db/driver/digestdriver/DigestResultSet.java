package com.is.util.db.driver.digestdriver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * Wraps PostgreSQL's ResultSet.
 * 
 * Performs data manipulation of input / output parameters, where needed to make
 * behavior similar to Oracle's JDBC driver
 */
public class DigestResultSet implements ResultSet{
	
	ResultSet mRSet;
	DigestStatement mParentStatement;
	protected boolean mDebugMode;
	
	public DigestResultSet(ResultSet aRSet, DigestStatement aParentStatement, boolean aDebugMode){
		mRSet = aRSet;
		mParentStatement = aParentStatement;
		mDebugMode = aDebugMode;
	}
	
	public void clearWarnings() throws SQLException {
		mRSet.clearWarnings();
	}

	public void close() throws SQLException {
		mRSet.close();
	}

	public int findColumn(String arg0) throws SQLException {
		return mRSet.findColumn(arg0);
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		return mRSet.getAsciiStream(arg0);
	}

	public InputStream getAsciiStream(String arg0) throws SQLException {
		return mRSet.getAsciiStream(arg0);
	}

	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		return OutParams.getBigDecimal(mRSet, arg0, arg1, mDebugMode);
	}

	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		return OutParams.getBigDecimal(mRSet, arg0, arg1, mDebugMode);
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		return mRSet.getBinaryStream(arg0);
	}

	public InputStream getBinaryStream(String arg0) throws SQLException {
		return mRSet.getBinaryStream(arg0);
	}

	public boolean getBoolean(int arg0) throws SQLException {
		return OutParams.getBoolean(mRSet, arg0, mDebugMode);
	}

	public boolean getBoolean(String arg0) throws SQLException {
		return OutParams.getBoolean(mRSet, arg0, mDebugMode);
	}

	public byte getByte(int arg0) throws SQLException {
		return OutParams.getByte(mRSet, arg0, mDebugMode);
	}

	public byte getByte(String arg0) throws SQLException {
		return OutParams.getByte(mRSet, arg0, mDebugMode);
	}

	public byte[] getBytes(int arg0) throws SQLException {
		return mRSet.getBytes(arg0);
	}

	public byte[] getBytes(String arg0) throws SQLException {
		return mRSet.getBytes(arg0);
	}

	public String getCursorName() throws SQLException {
		return mRSet.getCursorName();
	}

	public Date getDate(int arg0) throws SQLException {
		return OutParams.getDate(mRSet, arg0, mDebugMode);
	}

	public Date getDate(String arg0) throws SQLException {
		return OutParams.getDate(mRSet, arg0, mDebugMode);
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		return OutParams.getDate(mRSet, arg0, mDebugMode);
	}
	
	public double getDouble(String arg0) throws SQLException {
		return OutParams.getDouble(mRSet, arg0, mDebugMode);
	}

	public double getDouble(int arg0) throws SQLException {
		return OutParams.getDouble(mRSet, arg0, mDebugMode);
	}

	public float getFloat(int arg0) throws SQLException {
		return OutParams.getFloat(mRSet, arg0, mDebugMode);
	}

	public float getFloat(String arg0) throws SQLException {
		return OutParams.getFloat(mRSet, arg0, mDebugMode);
	}

	public int getInt(int arg0) throws SQLException {
		return OutParams.getInt(mRSet, arg0, mDebugMode);
	}

	public int getInt(String arg0) throws SQLException {
		return OutParams.getInt(mRSet, arg0, mDebugMode);
	}

	public long getLong(int arg0) throws SQLException {
		return OutParams.getLong(mRSet, arg0, mDebugMode);
	}

	public long getLong(String arg0) throws SQLException {
		return OutParams.getLong(mRSet, arg0, mDebugMode);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return mRSet.getMetaData();
	}

	public Object getObject(int arg0) throws SQLException {
		return mRSet.getObject(arg0);
	}

	public Object getObject(String arg0) throws SQLException {
		return mRSet.getObject(arg0);
	}

	public short getShort(String arg0) throws SQLException {
		return OutParams.getShort(mRSet, arg0, mDebugMode);
	}

	public short getShort(int arg0) throws SQLException {
		return OutParams.getShort(mRSet, arg0, mDebugMode);
	}

	public String getString(int arg0) throws SQLException {
		return OutParams.getString(mRSet, arg0, mDebugMode);
	}

	public String getString(String arg0) throws SQLException {
		return OutParams.getString(mRSet, arg0, mDebugMode);
	}

	public Time getTime(int arg0) throws SQLException {
		return OutParams.getTime(mRSet, arg0, mDebugMode);
	}

	public Time getTime(String arg0) throws SQLException {
		return OutParams.getTime(mRSet, arg0, mDebugMode);
	}
	
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		return OutParams.getTime(mRSet, arg0, mDebugMode);
	}

	public Timestamp getTimestamp(int arg0) throws SQLException {
		return OutParams.getTimestamp(mRSet, arg0, mDebugMode);
	}
	
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		return OutParams.getTimestamp(mRSet, arg0, mDebugMode);
	}

	public Timestamp getTimestamp(String arg0) throws SQLException {
		return OutParams.getTimestamp(mRSet, arg0, mDebugMode);
	}
	
	public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
		return OutParams.getTimestamp(mRSet, arg0, mDebugMode);
	}

	public InputStream getUnicodeStream(int arg0) throws SQLException {
		return mRSet.getUnicodeStream(arg0);
	}

	public InputStream getUnicodeStream(String arg0) throws SQLException {
		return mRSet.getUnicodeStream(arg0);
	}

	public SQLWarning getWarnings() throws SQLException {
		return mRSet.getWarnings();
	}

	public boolean next() throws SQLException {
		return mRSet.next();
	}

	public boolean wasNull() throws SQLException {
		return mRSet.wasNull();
	}

	public java.io.Reader getCharacterStream(int columnIndex) throws SQLException {
    	return mRSet.getCharacterStream(columnIndex);
    }

	public java.io.Reader getCharacterStream(String columnName) throws SQLException {
    	return mRSet.getCharacterStream(columnName);
    }

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return OutParams.getBigDecimal(mRSet, columnIndex, mDebugMode);
    }

	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		return OutParams.getBigDecimal(mRSet, columnName, mDebugMode);
    }

	public boolean isBeforeFirst() throws SQLException {
    	return mRSet.isBeforeFirst();
    }

	public boolean isAfterLast() throws SQLException {
    	return mRSet.isAfterLast();
    }

	public boolean isFirst() throws SQLException {
    	return mRSet.isFirst();
    }

	public boolean isLast() throws SQLException {
    	return mRSet.isLast();
    }

	public void beforeFirst() throws SQLException {
    	mRSet.beforeFirst();
    }
  
	public void afterLast() throws SQLException {
    	mRSet.afterLast();
    }

	public boolean first() throws SQLException {
    	return mRSet.first();
    }

	public boolean last() throws SQLException {
    	return mRSet.last();
    }

	public int getRow() throws SQLException {
    	return mRSet.getRow();
    }

	public boolean absolute( int row ) throws SQLException {
    	return mRSet.absolute( row );
    }

	public boolean relative( int rows ) throws SQLException {
    	return mRSet.relative( rows );
    }

	public boolean previous() throws SQLException {
    	return mRSet.previous();
    }

	public void setFetchDirection(int direction) throws SQLException {
    	mRSet.setFetchDirection(direction);
    }

	public int getFetchDirection() throws SQLException {
    	return mRSet.getFetchDirection();
    }

	public void setFetchSize(int rows) throws SQLException {
    	mRSet.setFetchSize(rows);
    }

	public int getFetchSize() throws SQLException {
    	return mRSet.getFetchSize();
    }

	public int getType() throws SQLException {    	
    	return mRSet.getType();
    }

	public int getConcurrency() throws SQLException {
    	return mRSet.getConcurrency();
    }

	public boolean rowUpdated() throws SQLException {
    	return mRSet.rowUpdated();
    }

	public boolean rowInserted() throws SQLException {
    	return mRSet.rowInserted();
    }

	public boolean rowDeleted() throws SQLException {
    	return mRSet.rowDeleted();
    }

	public void updateNull(int columnIndex) throws SQLException {
    	mRSet.updateNull(columnIndex);
    }  

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    	mRSet.updateBoolean(columnIndex, x);
    }

	public void updateByte(int columnIndex, byte x) throws SQLException {
    	mRSet.updateByte(columnIndex, x);
    }

	public void updateShort(int columnIndex, short x) throws SQLException {
    	mRSet.updateShort(columnIndex, x);
    }

	public void updateInt(int columnIndex, int x) throws SQLException {
    	mRSet.updateInt(columnIndex, x);
    }

	public void updateLong(int columnIndex, long x) throws SQLException {
    	mRSet.updateLong(columnIndex, x);
    }

	public void updateFloat(int columnIndex, float x) throws SQLException {
    	mRSet.updateFloat(columnIndex, x);
    }
    
	public void updateDouble(int columnIndex, double x) throws SQLException {
    	mRSet.updateDouble(columnIndex, x);
    }

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    	mRSet.updateBigDecimal(columnIndex, x);
    }

	public void updateString(int columnIndex, String x) throws SQLException {
    	mRSet.updateString(columnIndex, x);
    }

	public void updateBytes(int columnIndex, byte x[]) throws SQLException {
    	mRSet.updateBytes(columnIndex, x);
    }

	public void updateDate(int columnIndex, java.sql.Date x) throws SQLException {
    	mRSet.updateDate(columnIndex, x);
    }

	public void updateTime(int columnIndex, java.sql.Time x) throws SQLException {
    	mRSet.updateTime(columnIndex, x);
    }

	public void updateTimestamp(int columnIndex, java.sql.Timestamp x) throws SQLException {
    	mRSet.updateTimestamp(columnIndex, x);
    }
   
	public void updateAsciiStream(int columnIndex, java.io.InputStream x, int length) throws SQLException {
    	mRSet.updateAsciiStream(columnIndex, x, length);
    }
    
	public void updateBinaryStream(int columnIndex, java.io.InputStream x, int length) throws SQLException {
    	mRSet.updateBinaryStream(columnIndex, x, length);
    }

	public void updateCharacterStream(int columnIndex, java.io.Reader x, int length) throws SQLException {
    	mRSet.updateCharacterStream(columnIndex, x, length);
    }
    
	public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
    	mRSet.updateObject(columnIndex, x, scale);
    }
    
	public void updateObject(int columnIndex, Object x) throws SQLException {
    	mRSet.updateObject(columnIndex, x);
    }

	public void updateNull(String columnName) throws SQLException {
    	mRSet.updateNull(columnName);
    }  

	public void updateBoolean(String columnName, boolean x) throws SQLException {
    	mRSet.updateBoolean(columnName, x);
    }

	public void updateByte(String columnName, byte x) throws SQLException {
    	mRSet.updateByte(columnName, x);
    }

	public void updateShort(String columnName, short x) throws SQLException {
    	mRSet.updateShort(columnName, x);
    }
   
	public void updateInt(String columnName, int x) throws SQLException {
    	mRSet.updateInt(columnName, x);
    }

	public void updateLong(String columnName, long x) throws SQLException {
    	mRSet.updateLong(columnName, x);
    }

	public void updateFloat(String columnName, float x) throws SQLException {
    	mRSet.updateFloat(columnName, x);
    }

	public void updateDouble(String columnName, double x) throws SQLException {
    	mRSet.updateDouble(columnName, x);
    }

	public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
    	mRSet.updateBigDecimal(columnName, x);
    }

	public void updateString(String columnName, String x) throws SQLException {
    	mRSet.updateString(columnName, x);
    }

	public void updateBytes(String columnName, byte x[]) throws SQLException {
    	mRSet.updateBytes(columnName, x);
    }

	public void updateDate(String columnName, java.sql.Date x) throws SQLException {
    	mRSet.updateDate(columnName, x);
    }

	public void updateTime(String columnName, java.sql.Time x) throws SQLException {
    	mRSet.updateTime(columnName, x);
    }

	public void updateTimestamp(String columnName, java.sql.Timestamp x) throws SQLException {
    	mRSet.updateTimestamp(columnName, x);
    }

	public void updateAsciiStream(String columnName, java.io.InputStream x, int length) throws SQLException {
    	mRSet.updateAsciiStream(columnName, x, length);
    }

	public void updateBinaryStream(String columnName, java.io.InputStream x, int length) throws SQLException {
    	mRSet.updateBinaryStream(columnName, x, length);
    }

	public void updateCharacterStream(String columnName, java.io.Reader reader, int length) throws SQLException {
    	mRSet.updateCharacterStream(columnName, reader, length);
    }

	public void updateObject(String columnName, Object x, int scale) throws SQLException {
    	mRSet.updateObject(columnName, x, scale);
    }

	public void updateObject(String columnName, Object x) throws SQLException {
    	mRSet.updateObject(columnName, x);
    }

	public void insertRow() throws SQLException {
    	mRSet.insertRow();
    }

	public void updateRow() throws SQLException {
    	mRSet.updateRow();
    }

	public void deleteRow() throws SQLException {
    	mRSet.deleteRow();
    }

	public void refreshRow() throws SQLException {
    	mRSet.refreshRow();
    }

	public void cancelRowUpdates() throws SQLException {
    	mRSet.cancelRowUpdates();
    }

	public void moveToInsertRow() throws SQLException {
    	mRSet.moveToInsertRow();
    }

	public void moveToCurrentRow() throws SQLException {
    	mRSet.moveToCurrentRow();
    }

	public Statement getStatement() throws SQLException {
    	return mParentStatement;
    }

	public Ref getRef(int i) throws SQLException {
    	return mRSet.getRef(i);
    }

	public Blob getBlob(int i) throws SQLException {
    	return mRSet.getBlob(i);
    }

	public Clob getClob(int i) throws SQLException {
    	return mRSet.getClob(i);
    }

	public Array getArray(int i) throws SQLException {
    	return mRSet.getArray(i);
    }

	public Ref getRef(String colName) throws SQLException {
    	return mRSet.getRef(colName);
    }

	public Blob getBlob(String colName) throws SQLException {
    	return mRSet.getBlob(colName);
    }

	public Clob getClob(String colName) throws SQLException {
    	return mRSet.getClob(colName);
    }

	public Array getArray(String colName) throws SQLException {
    	return mRSet.getArray(colName);
    }

	public java.sql.Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return OutParams.getDate(mRSet, columnIndex, mDebugMode);
    }

	public java.sql.Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return OutParams.getTime(mRSet, columnIndex, mDebugMode);
    }

	public java.net.URL getURL(int columnIndex) throws SQLException {
    	return mRSet.getURL(columnIndex);
    }

	public java.net.URL getURL(String columnName) throws SQLException {
    	return mRSet.getURL(columnName);
    }

	public void updateRef(int columnIndex, java.sql.Ref x) throws SQLException {
    	mRSet.updateRef(columnIndex, x);
    }

	public void updateRef(String columnName, java.sql.Ref x) throws SQLException {
    	mRSet.updateRef(columnName, x);
    }

	public void updateBlob(int columnIndex, java.sql.Blob x) throws SQLException {
    	mRSet.updateBlob(columnIndex, x);
    }

	public void updateBlob(String columnName, java.sql.Blob x) throws SQLException {
    	mRSet.updateBlob(columnName, x);
    }

	public void updateClob(int columnIndex, java.sql.Clob x) throws SQLException {
    	mRSet.updateClob(columnIndex, x);
    }

	public void updateClob(String columnName, java.sql.Clob x) throws SQLException {
    	mRSet.updateClob(columnName, x);
    }

	public void updateArray(int columnIndex, java.sql.Array x) throws SQLException {
    	mRSet.updateArray(columnIndex, x);
    }

	public void updateArray(String columnName, java.sql.Array x) throws SQLException {
    	mRSet.updateArray(columnName, x);
    }

	
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return mRSet.isWrapperFor(iface);
	}

	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return mRSet.unwrap(iface);
	}

	
	public int getHoldability() throws SQLException {
		return mRSet.getHoldability();
	}

	
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return mRSet.getCharacterStream(columnIndex);
	}

	
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return mRSet.getCharacterStream(columnLabel);
	}

	
	public NClob getNClob(int columnIndex) throws SQLException {
		return mRSet.getNClob(columnIndex);
	}

	
	public NClob getNClob(String columnLabel) throws SQLException {
		return mRSet.getNClob(columnLabel);
	}

	
	public String getNString(int columnIndex) throws SQLException {
		return mRSet.getString(columnIndex);
	}

	
	public String getNString(String columnLabel) throws SQLException {
		return mRSet.getString(columnLabel);
	}

	
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		return mRSet.getObject(columnIndex, map);
	}

	
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		return mRSet.getObject(columnLabel, map);
	}

	
	public RowId getRowId(int columnIndex) throws SQLException {
		return mRSet.getRowId(columnIndex);
	}

	
	public RowId getRowId(String columnLabel) throws SQLException {
		return mRSet.getRowId(columnLabel);
	}

	
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return mRSet.getSQLXML(columnIndex);
	}

	
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return mRSet.getSQLXML(columnLabel);
	}

	
	public boolean isClosed() throws SQLException {
		return mRSet.isClosed();
	}

	
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		mRSet.updateAsciiStream(columnIndex, x);
	}

	
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		mRSet.updateAsciiStream(columnLabel, x);
		
	}

	
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		mRSet.updateAsciiStream(columnIndex, x, length);
	}

	
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		mRSet.updateAsciiStream(columnLabel, x, length);
	}

	
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		mRSet.updateBinaryStream(columnIndex, x);
	}

	
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		mRSet.updateBinaryStream(columnLabel, x);
	}

	
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		mRSet.updateBinaryStream(columnIndex, x, length);
	}

	
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		mRSet.updateBinaryStream(columnLabel, x,length);
	}

	
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		mRSet.updateBlob(columnIndex, inputStream);
	}

	
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		mRSet.updateBlob(columnLabel, inputStream);
	}

	
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		mRSet.updateBlob(columnIndex, inputStream, length);
	}

	
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		mRSet.updateBlob(columnLabel, inputStream,length);
	}

	
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		mRSet.updateCharacterStream(columnIndex, x);
	}

	
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		mRSet.updateCharacterStream(columnLabel, reader);
	}

	
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		mRSet.updateCharacterStream(columnIndex, x, length);
	}

	
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		mRSet.updateCharacterStream(columnLabel, reader, length);
	}

	
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		mRSet.updateClob(columnIndex, reader);
	}

	
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		mRSet.updateClob(columnLabel, reader);
	}

	
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		mRSet.updateClob(columnIndex, reader, length);
	}

	
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		mRSet.updateClob(columnLabel, reader, length);
	}

	
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		mRSet.updateNCharacterStream(columnIndex, x);
	}

	
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		mRSet.updateNCharacterStream(columnLabel, reader);		
	}

	
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		mRSet.updateNCharacterStream(columnIndex, x, length);		
	}

	
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		mRSet.updateNCharacterStream(columnLabel, reader, length);
	}

	
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		mRSet.updateNClob(columnIndex, nClob);		
	}

	
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		mRSet.updateNClob(columnLabel, nClob);		
	}

	
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		mRSet.updateNClob(columnIndex, reader);		
	}

	
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		mRSet.updateNClob(columnLabel, reader);		
	}

	
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		mRSet.updateNClob(columnIndex, reader, length);		
	}

	
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		mRSet.updateNClob(columnLabel, reader, length);
	}

	
	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		mRSet.updateNString(columnIndex, nString);
	}

	
	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		mRSet.updateNString(columnLabel, nString);
	}

	
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		mRSet.updateRowId(columnIndex, x);
	}

	
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		mRSet.updateRowId(columnLabel, x);
	}

	
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		mRSet.updateSQLXML(columnIndex, xmlObject);
	}

	
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		mRSet.updateSQLXML(columnLabel, xmlObject);
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
