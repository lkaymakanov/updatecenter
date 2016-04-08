package com.is.util.db.driver.digestdriver;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import com.is.util.db.driver.digestdriver.Logger.Log;

public class OutParams {

	public static long getLong(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getLong(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toLong(cstmt.getObject(pos));
	}
	
	public static short getShort(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getShort(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toShort(cstmt.getObject(pos));
	}
	
	public static float getFloat(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getFloat(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toFloat(cstmt.getObject(pos));
	}
	
	public static double getDouble(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getDouble(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toDouble(cstmt.getObject(pos));
	}
	
	public static int getInt(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getInt(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toInteger(cstmt.getObject(pos));
	}
	
	public static byte getByte(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getByte(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toByte(cstmt.getObject(pos));
	}
	
	public static BigDecimal getBigDecimal(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBigDecimal(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBigDecimal(cstmt.getObject(pos));
	}
	
	public static BigDecimal getBigDecimal(CallableStatement cstmt, int pos, int scale, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBigDecimal(pos).setScale(scale);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBigDecimal(cstmt.getObject(pos)).setScale(scale);
	}
	
	public static String getString(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getString(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toString(cstmt.getObject(pos));
	}
	
	public static long getLong(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getLong(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toLong(rs.getObject(pos));
	}
	
	public static short getShort(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getShort(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toShort(rs.getObject(pos));
	}
	
	public static float getFloat(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getFloat(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toFloat(rs.getObject(pos));
	}
	
	public static double getDouble(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getDouble(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toDouble(rs.getObject(pos));
	}
	
	public static int getInt(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getInt(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toInteger(rs.getObject(pos));
	}
	
	public static byte getByte(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getByte(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toByte(rs.getObject(pos));
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBigDecimal(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBigDecimal(rs.getObject(pos));
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, int pos, int scale, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBigDecimal(pos).setScale(scale);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBigDecimal(rs.getObject(pos)).setScale(scale);
	}
	
	public static String getString(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getString(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toString(rs.getObject(pos));
	}
	
	public static Reader getCharacterStream(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getCharacterStream(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return rs.getCharacterStream(pos);
	}
	
	public static Boolean getBoolean(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBoolean(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBoolean(rs.getObject(pos));
	}
	
	public static Boolean getBoolean(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBoolean(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBoolean(rs.getObject(col));
	}
	
	public static long getLong(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getLong(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toLong(cstmt.getObject(col));
	}
	
	public static short getShort(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getShort(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toShort(cstmt.getObject(col));
	}
	
	public static float getFloat(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getFloat(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toFloat(cstmt.getObject(col));
	}
	
	public static double getDouble(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getDouble(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toDouble(cstmt.getObject(col));
	}
	
	public static int getInt(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getInt(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toInteger(cstmt.getObject(col));
	}
	
	public static byte getByte(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getByte(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toByte(cstmt.getObject(col));
	}
	
	public static BigDecimal getBigDecimal(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBigDecimal(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBigDecimal(cstmt.getObject(col));
	}
	
	public static BigDecimal getBigDecimal(CallableStatement cstmt, String col, int scale, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBigDecimal(col).setScale(scale);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBigDecimal(cstmt.getObject(col)).setScale(scale);
	}
	
	public static String getString(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getString(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toString(cstmt.getObject(col));
	}
	
	public static long getLong(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getLong(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toLong(rs.getObject(col));
	}
	
	public static short getShort(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getShort(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toShort(rs.getObject(col));
	}
	
	public static float getFloat(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getFloat(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toFloat(rs.getObject(col));
	}
	
	public static double getDouble(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getDouble(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toDouble(rs.getObject(col));
	}
	
	public static int getInt(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getInt(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toInteger(rs.getObject(col));
	}
	
	public static byte getByte(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getByte(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toByte(rs.getObject(col));
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBigDecimal(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBigDecimal(rs.getObject(col));
	}
	
	public static BigDecimal getBigDecimal(ResultSet rs, String col, int scale, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getBigDecimal(col).setScale(scale);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBigDecimal(rs.getObject(col)).setScale(scale);
	}
	
	public static String getString(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getString(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toString(rs.getObject(col));
	}
	
	public static Reader getCharacterStream(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getCharacterStream(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return rs.getCharacterStream(col);
	}
	
	public static Date getDate(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getDate(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toDate(rs.getObject(col));
	}
	
	public static Timestamp getTimestamp(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getTimestamp(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toTimestamp(rs.getObject(col));
	}
	
	public static Time getTime(ResultSet rs, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getTime(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toTime(rs.getObject(col));
	}
	
	public static Date getDate(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getDate(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toDate(cstmt.getObject(pos));
	}
	
	public static Timestamp getTimestamp(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getTimestamp(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toTimestamp(cstmt.getObject(pos));
	}
	
	public static Time getTime(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getTime(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toTime(cstmt.getObject(pos));
	}
	
	public static Date getDate(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getDate(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toDate(rs.getObject(pos));
	}
	
	public static Timestamp getTimestamp(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getTimestamp(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toTimestamp(rs.getObject(pos));
	}
	
	public static Time getTime(ResultSet rs, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return rs.getTime(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toTime(rs.getObject(pos));
	}
	
	public static Date getDate(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getDate(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toDate(cstmt.getObject(col));
	}
	
	public static Timestamp getTimestamp(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getTimestamp(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toTimestamp(cstmt.getObject(col));
	}
	
	public static Time getTime(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getTime(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toTime(cstmt.getObject(col));
	}
	
	public static Boolean getBoolean(CallableStatement cstmt, String col, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBoolean(col);
			} catch (Exception e) {
				Log.convErr(e, col);
			}
		}
		return toBoolean(cstmt.getObject(col));
	}
	
	public static Boolean getBoolean(CallableStatement cstmt, int pos, boolean debugOutParams) throws SQLException {
		if (debugOutParams) {
			try {
				return cstmt.getBoolean(pos);
			} catch (Exception e) {
				Log.convErr(e, new Integer(pos).toString());
			}
		}
		return toBoolean(cstmt.getObject(pos));
	}
	
	
	protected static long toLong(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).longValue();
	}
	
	protected static int toInteger(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).intValue();
	}
	
	protected static short toShort(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).shortValue();
	}
	
	protected static byte toByte(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).byteValue();
	}
	
	protected static BigDecimal toBigDecimal(Object s) {
		if (s==null) return null;
		return new BigDecimal(s.toString());
	}
	
	protected static float toFloat(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).floatValue();
	}
	
	protected static double toDouble(Object s) {
		if (s==null) return 0;
		return new BigDecimal(s.toString()).doubleValue();
	}
	
	protected static String toString(Object s) {
		if (s==null) return null;
		return s.toString();
	}

	private static Boolean toBoolean(Object s) {
		if (s==null) return null;
		if (s instanceof Number) {
			int num = new Double(s.toString()).intValue();
			return num==0? false : true;
		}
		return new Boolean(s.toString());
	}
	
	public static Date toDate(Object object) {
		if (object==null) return null;
		if (object instanceof Timestamp) return new Date(((Timestamp)object).getTime());
		if (object instanceof Time) return new Date(((Time)object).getTime());
		return (Date)object;
	}
	
	public static Timestamp toTimestamp(Object object) {
		if (object==null) return null;
		if (object instanceof Date) return new Timestamp(((Date)object).getTime());
		if (object instanceof Time) return new Timestamp(((Time)object).getTime());
		return (Timestamp)object;
	}
	
	public static Time toTime(Object object) {
		if (object==null) return null;
		if (object instanceof Timestamp) return new Time(((Timestamp)object).getTime());
		if (object instanceof Date) return new Time(((Date)object).getTime());
		return (Time)object;
	}
	
	
}