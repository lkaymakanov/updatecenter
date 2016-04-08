package com.is.util.db.driver.digestdriver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.is.util.db.driver.digestdriver.Logger.Log;
import com.is.util.db.driver.digestdriver.prochelper.FunctionParams;
import com.is.util.db.driver.digestdriver.prochelper.MetaDataHelper;

public class DigestCallableStatement extends DigestPreparedStatement implements CallableStatement {

	protected FunctionParams 	mParams;
	
	public DigestCallableStatement(String aSql, CallableStatement aCStmt, DigestConnection aParentConnection, String aQuery, Connection aTestConnection, boolean aDebugMode) {
		super(aCStmt, aParentConnection, aQuery, aTestConnection, aDebugMode);
		String funcName[] = detectFunctionName(aSql);
		boolean hasReturnParam = hasReturnParam(aSql);
		try {
			mParams = MetaDataHelper.getFunctionParams(this, funcName[0], funcName[1], hasReturnParam);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public void registerOutParameter(int i, int j) throws SQLException {
		Integer type = mParams.getParam(i).getColumnDataType();
		if (type!=null && type.intValue()!=j) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), new Integer(i).toString());
			j = type.intValue();
		}
		((CallableStatement)mPStmt).registerOutParameter(i, j);
	}

	
	public void registerOutParameter(int i, int j, int k) throws SQLException {
		Integer type = mParams.getParam(i).getColumnDataType();
		if (type!=null && type.intValue()!=j) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), new Integer(i).toString());
			j = type.intValue();
		}
		((CallableStatement)mPStmt).registerOutParameter(i, j, k);
	}

	
	public boolean wasNull() throws SQLException {
		return ((CallableStatement)mPStmt).wasNull();
	}

	
	public String getString(int i) throws SQLException {
		return OutParams.getString((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public boolean getBoolean(int i) throws SQLException {
		return OutParams.getBoolean((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public byte getByte(int i) throws SQLException {
		return OutParams.getByte((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public short getShort(int i) throws SQLException {
		return OutParams.getShort((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public int getInt(int i) throws SQLException {
		return OutParams.getInt((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public long getLong(int i) throws SQLException {
		return OutParams.getLong((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public float getFloat(int i) throws SQLException {
		return OutParams.getFloat((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public double getDouble(int i) throws SQLException {
		return OutParams.getDouble((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public BigDecimal getBigDecimal(int i, int j) throws SQLException {
		return OutParams.getBigDecimal((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public byte[] getBytes(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getBytes(i);
	}

	
	public Date getDate(int i) throws SQLException {
		return OutParams.getDate((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Time getTime(int i) throws SQLException {
		return OutParams.getTime((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Timestamp getTimestamp(int i) throws SQLException {
		return OutParams.getTimestamp((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Object getObject(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getObject(i);
	}

	
	public BigDecimal getBigDecimal(int i) throws SQLException {
		return OutParams.getBigDecimal((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Blob getBlob(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getBlob(i);
	}

	
	public Clob getClob(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getClob(i);
	}

	
	public Array getArray(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getArray(i);
	}

	
	public Date getDate(int i, Calendar calendar) throws SQLException {
		return OutParams.getDate((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Time getTime(int i, Calendar calendar) throws SQLException {
		return OutParams.getTime((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
		return OutParams.getTimestamp((CallableStatement)mPStmt, i, mDebugMode);
	}

	
	public void registerOutParameter(int i, int j, String s)
			throws SQLException {
		Integer type = mParams.getParam(i).getColumnDataType();
		if (type!=null && type.intValue()!=j) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), new Integer(i).toString());
			i = type.intValue();
		}
		((CallableStatement)mPStmt).registerOutParameter(i, j, s);
	}

	
	public void registerOutParameter(String s, int i) throws SQLException {
		Integer type = mParams.getParam(s).getColumnDataType();
		if (type!=null && type.intValue()!=i) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), new Integer(i).toString());
			i = type.intValue();
		}
		try {
			((CallableStatement)mPStmt).registerOutParameter(s, i);
		} catch (Exception e) {
			Log.convErr(e, s);
			((CallableStatement)mPStmt).registerOutParameter(mParams.getParamIndex(s), i);
		}
	}

	
	public void registerOutParameter(String s, int i, int j)
			throws SQLException {
		Integer type = mParams.getParam(s).getColumnDataType();
		if (type!=null && type.intValue()!=i) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), new Integer(i).toString());
			i = type.intValue();
		}
		try {
			((CallableStatement)mPStmt).registerOutParameter(s, i, j);
		} catch (Exception e) {
			Log.convErr(e,s);
			((CallableStatement)mPStmt).registerOutParameter(mParams.getParamIndex(s), i, j);
		}
	}

	
	public void registerOutParameter(String s, int i, String s1)
			throws SQLException {
		Integer type = mParams.getParam(s).getColumnDataType();
		if (type!=null && type.intValue()!=i) {
			Log.convErr(new Exception("Invalid sql type specified for registerOutParameter! Passed type="+i+" should be "+type.toString()), s);
			i = type.intValue();
		}
		try {
			((CallableStatement)mPStmt).registerOutParameter(s, i, s1);
		} catch (Exception e) {
			Log.convErr(e, s);
			((CallableStatement)mPStmt).registerOutParameter(mParams.getParamIndex(s), i, s1);
		}
	}

	
	public URL getURL(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getURL(i);
	}

	
	public void setURL(String s, URL url) throws SQLException {
		((CallableStatement)mPStmt).setURL(s, url);
	}

	
	public void setNull(String s, int i) throws SQLException {
		((CallableStatement)mPStmt).setNull(s, i);
	}

	
	public void setBoolean(String s, boolean flag) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), flag, mQuery, SettersEnum.setByte, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setByte(String s, byte byte0) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), byte0, mQuery, SettersEnum.setByte, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setShort(String s, short word0) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), word0, mQuery, SettersEnum.setShort, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setInt(String s, int i) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), i, mQuery, SettersEnum.setInt, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setLong(String s, long l) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), l, mQuery, SettersEnum.setLong, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setFloat(String s, float f) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), f, mQuery, SettersEnum.setFloat, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setDouble(String s, double d) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), d, mQuery, SettersEnum.setDouble, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setBigDecimal(String s, BigDecimal bigdecimal)
			throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), bigdecimal, mQuery, SettersEnum.setBigDecimal, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setString(String s, String s1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), s1, mQuery, SettersEnum.setString, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setBytes(String s, byte[] abyte0) throws SQLException {
		((CallableStatement)mPStmt).setBytes(mParams.getParamIndex(s), abyte0);
	}

	
	public void setDate(String s, Date date) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), date, mQuery, SettersEnum.setDate, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTime(String s, Time time) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), time, mQuery, SettersEnum.setTime, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTimestamp(String s, Timestamp timestamp) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), timestamp, mQuery, SettersEnum.setTimestamp, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setAsciiStream(String s, InputStream inputstream, int i)
			throws SQLException {
		((CallableStatement)mPStmt).setAsciiStream(s, inputstream, i);
	}

	
	public void setBinaryStream(String s, InputStream inputstream, int i)
			throws SQLException {
		((CallableStatement)mPStmt).setBinaryStream(s, inputstream, i);
	}

	
	public void setObject(String s, Object obj, int i, int j)
			throws SQLException {
//		if (s!=null) {
		Class paramClass = obj!=null? obj.getClass() : Object.class;
			InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), obj, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			((CallableStatement)mPStmt).setObject(s, obj, i, j);
//		}
	}

	
	public void setObject(String s, Object obj, int i) throws SQLException {
//		if (s!=null) {
		Class paramClass = obj!=null? obj.getClass() : Object.class;
			InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), obj, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			((CallableStatement)mPStmt).setObject(s, obj, i);
//		}
	}

	
	public void setObject(String s, Object obj) throws SQLException {
//		if (s!=null) {
		Class paramClass = obj!=null? obj.getClass() : Object.class;
			InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), obj, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
//		} else {
//			((CallableStatement)mPStmt).setObject(s, obj);
//		}
	}

	
	public void setCharacterStream(String s, Reader reader, int i)
			throws SQLException {
			((CallableStatement)mPStmt).setCharacterStream(s, reader, i);
	}

	
	public void setDate(String s, Date date, Calendar calendar)
			throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), date, mQuery, SettersEnum.setDate, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTime(String s, Time time, Calendar calendar)
			throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), time, mQuery, SettersEnum.setTime, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTimestamp(String s, Timestamp timestamp, Calendar calendar)
			throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), timestamp, mQuery, SettersEnum.setTimestamp, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setNull(String s, int i, String s1) throws SQLException {
		((CallableStatement)mPStmt).setNull(s, i, s1);
	}

	
	public String getString(String s) throws SQLException {
		return OutParams.getString((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public boolean getBoolean(String s) throws SQLException {
		return OutParams.getBoolean((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public byte getByte(String s) throws SQLException {
		return OutParams.getByte((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public short getShort(String s) throws SQLException {
		return OutParams.getShort((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public int getInt(String s) throws SQLException {
		return OutParams.getInt((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public long getLong(String s) throws SQLException {
		return OutParams.getLong((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public float getFloat(String s) throws SQLException {
		return OutParams.getFloat((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public double getDouble(String s) throws SQLException {
		return OutParams.getDouble((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public byte[] getBytes(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getBytes(mParams.getParamIndex(s));
	}

	
	public Date getDate(String s) throws SQLException {
		return OutParams.getDate((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Time getTime(String s) throws SQLException {
		return OutParams.getTime((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Timestamp getTimestamp(String s) throws SQLException {
		return OutParams.getTimestamp((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Object getObject(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getObject(s);
	}

	
	public BigDecimal getBigDecimal(String s) throws SQLException {
		return OutParams.getBigDecimal((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Ref getRef(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getRef(s);
	}

	
	public Blob getBlob(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getBlob(s);
	}

	
	public Clob getClob(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getClob(s);
	}

	
	public Array getArray(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getArray(s);
	}

	
	public Date getDate(String s, Calendar calendar) throws SQLException {
		return OutParams.getDate((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Time getTime(String s, Calendar calendar) throws SQLException {
		return OutParams.getTime((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public Timestamp getTimestamp(String s, Calendar calendar)
			throws SQLException {
		return OutParams.getTimestamp((CallableStatement)mPStmt, s, mDebugMode);
	}

	
	public URL getURL(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getURL(s);
	}

	
	public RowId getRowId(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getRowId(i);
	}

	
	public RowId getRowId(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getRowId(s);
	}

	
	public void setRowId(String s, RowId rowid) throws SQLException {
		((CallableStatement)mPStmt).setRowId(s, rowid);
	}

	
	public void setNString(String s, String s1) throws SQLException {
		if (mDebugMode) {
			Log.convErr(new Exception("<PreparedStatement>.setNString should not be used! PostgreSQL does not supports it."), s);
		}
		InParams.setCallParameter((CallableStatement)mPStmt, mParams.getParamIndex(s), s1, mQuery, SettersEnum.setString, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setNCharacterStream(String s, Reader reader, long l)
			throws SQLException {
		((CallableStatement)mPStmt).setNCharacterStream(s, reader, l);
	}

	
	public void setNClob(String s, NClob nclob) throws SQLException {
		((CallableStatement)mPStmt).setNClob(s, nclob);
	}

	
	public void setClob(String s, Reader reader, long l) throws SQLException {
		((CallableStatement)mPStmt).setClob(s, reader, l);
	}

	
	public void setBlob(String s, InputStream inputstream, long l)
			throws SQLException {
		((CallableStatement)mPStmt).setBlob(s, inputstream, l);
	}

	
	public void setNClob(String s, Reader reader, long l) throws SQLException {
		((CallableStatement)mPStmt).setNClob(s, reader, l);
	}

	
	public NClob getNClob(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getNClob(i);
	}

	
	public NClob getNClob(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getNClob(s);
	}

	
	public void setSQLXML(String s, SQLXML sqlxml) throws SQLException {
		((CallableStatement)mPStmt).setSQLXML(s, sqlxml);
	}

	
	public SQLXML getSQLXML(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getSQLXML(i);
	}

	
	public SQLXML getSQLXML(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getSQLXML(s);
	}

	
	public String getNString(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getNString(i);
	}

	
	public String getNString(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getNString(s);
	}

	
	public Reader getNCharacterStream(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getNCharacterStream(i);
	}

	
	public Reader getNCharacterStream(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getNCharacterStream(s);
	}

	
	public Reader getCharacterStream(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getCharacterStream(i);
	}

	
	public Reader getCharacterStream(String s) throws SQLException {
		return ((CallableStatement)mPStmt).getCharacterStream(s);
	}

	
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
			throws SQLException {
		return ((CallableStatement)mPStmt).getObject(parameterIndex, map);
	}

	
	public Object getObject(String parameterName, Map<String, Class<?>> map)
			throws SQLException {
		return ((CallableStatement)mPStmt).getObject(parameterName, map);
	}

	
	public Ref getRef(int i) throws SQLException {
		return ((CallableStatement)mPStmt).getRef(i);
	}

	
	public void setBlob(String s, Blob blob) throws SQLException {
		((CallableStatement)mPStmt).setBlob(s, blob);
	}

	
	public void setClob(String s, Clob clob) throws SQLException {
		((CallableStatement)mPStmt).setClob(s, clob);
	}

	
	public void setAsciiStream(String s, InputStream inputstream, long l)
			throws SQLException {
		((CallableStatement)mPStmt).setAsciiStream(s, inputstream, l);
	}

	
	public void setBinaryStream(String s, InputStream inputstream, long l)
			throws SQLException {
		((CallableStatement)mPStmt).setBinaryStream(s, inputstream, l);
	}

	
	public void setCharacterStream(String s, Reader reader, long l)
			throws SQLException {
		((CallableStatement)mPStmt).setCharacterStream(s, reader, l);
	}

	
	public void setAsciiStream(String s, InputStream inputstream)
			throws SQLException {
		((CallableStatement)mPStmt).setAsciiStream(s, inputstream);
	}

	
	public void setBinaryStream(String s, InputStream inputstream)
			throws SQLException {
		((CallableStatement)mPStmt).setBinaryStream(s, inputstream);
	}

	
	public void setCharacterStream(String s, Reader reader) throws SQLException {
		((CallableStatement)mPStmt).setCharacterStream(s, reader);
	}

	
	public void setNCharacterStream(String s, Reader reader)
			throws SQLException {
		((CallableStatement)mPStmt).setNCharacterStream(s, reader);
	}

	
	public void setClob(String s, Reader reader) throws SQLException {
		((CallableStatement)mPStmt).setClob(s, reader);
	}

	
	public void setBlob(String s, InputStream inputstream) throws SQLException {
		((CallableStatement)mPStmt).setBlob(s, inputstream);
	}

	
	public void setNClob(String s, Reader reader) throws SQLException {
		((CallableStatement)mPStmt).setNClob(s, reader);
	}

	
	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, new Boolean(arg1), mQuery, SettersEnum.setBoolean, mParams, mTestConnection, mDebugMode);
	}

	
	public void setByte(int arg0, byte arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setByte, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setDate(int arg0, Date arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setDate, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setDate, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setDouble(int arg0, double arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setDouble, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setFloat(int arg0, float arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setFloat, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setInt(int arg0, int arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setInt, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setLong(int arg0, long arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setLong, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setShort(int arg0, short arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setShort, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setString(int arg0, String arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setString, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTime(int arg0, Time arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setTime, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setTime, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setTimestamp, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2)
			throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setTimestamp, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}
	
	
	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.setBigDecimal, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setNull(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		super.setNull(arg0, arg1);
	}

	
	public void setNull(int paramIndex, int sqlType, String typeName)
			throws SQLException {
		// TODO Auto-generated method stub
		super.setNull(paramIndex, sqlType, typeName);
	}

	
	public void setNString(int i, String s) throws SQLException {
		Log.convErr(new Exception("<PreparedStatement>.setNString should not be used! PostgreSQL does not supports it."), ""+i);
		InParams.setCallParameter((CallableStatement)mPStmt, i, s, mQuery, SettersEnum.setString, mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setObject(int arg0, Object arg1) throws SQLException {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	
	public void setObject(int arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		Class paramClass = arg1!=null? arg1.getClass() : Object.class;
		InParams.setCallParameter((CallableStatement)mPStmt, arg0, arg1, mQuery, SettersEnum.getByParameterType(paramClass), mParams, mParentConnection.getTestConnection(), mDebugMode);
	}

	protected String[] detectFunctionName(String aSql) {
		String fullName = aSql.replaceFirst(Constants.CALL_STMT_BEGIN, "").replaceFirst(Constants.CALL_STMT_END, "");
		if (fullName.indexOf('.')<0) return new String[]{null, fullName};
		return new String[]{ fullName.substring(0, fullName.indexOf('.')), fullName.substring(fullName.indexOf('.')+1) };
	}
	
	protected boolean hasReturnParam(String aSql) {
		return aSql.matches(Constants.HAS_RETURN_PARAM_CALL_STMT_BEGIN);
	}


	@Override
	public <T> T getObject(int parameterIndex, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T getObject(String parameterName, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
