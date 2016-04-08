package com.is.util.db.driver.digestdriver;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;

import com.is.util.db.driver.digestdriver.Logger.Log;
import com.is.util.db.driver.digestdriver.prochelper.FunctionParams;

public class InParams {

	public static void setCallParameter(CallableStatement aStmt, int aPos, Object aVal, String aQuery, SettersEnum aOriginalSetter, FunctionParams aFuncParams, final Connection aTestConnection, boolean aDebugMode) throws SQLException {
		TestStatementFactory testStatementFactory = new TestStatementFactory() {
			
			public PreparedStatement createTestStatement(Statement aStmt, String aQuery) throws SQLException {
				return aTestConnection.prepareCall(aQuery);
			}
		};

		setParameterHeuristivally(aStmt, aPos, aVal, aQuery, aOriginalSetter, aFuncParams, testStatementFactory, aDebugMode);
	}
	
	public static void setPreparedParameter(PreparedStatement aStmt, int aPos, Object aVal, String aQuery, SettersEnum aOriginalSetter, final Connection aTestConnection, boolean aDebugMode) throws SQLException {
		
		TestStatementFactory testStatementFactory = new TestStatementFactory() {
			
			public PreparedStatement createTestStatement(Statement aStmt, String aQuery) throws SQLException {
				return aTestConnection.prepareStatement(aQuery);
			}
		};

		setParameterHeuristivally(aStmt, aPos, aVal, aQuery, aOriginalSetter, null, testStatementFactory, aDebugMode);
	}
	
	
	protected static void setParameterHeuristivally(PreparedStatement aStmt, int aPos, Object aVal, String aQuery, SettersEnum aOriginalSetter, FunctionParams aFuncParams, TestStatementFactory aTestStatementFactory, boolean aDebugMode) throws SQLException {

		if (aDebugMode) {
			//1 try original setter
			boolean original = trySingleSettterHeuristically(aStmt, aPos, aVal, aQuery, aOriginalSetter, aFuncParams, aTestStatementFactory);
			
			//if not successfull try all setters
			if (!original) {
				for(Iterator<SettersEnum> i=SettersEnum.iterator(); i.hasNext(); ) {
					SettersEnum setter = i.next(); 
					boolean result = trySingleSettterHeuristically(aStmt, aPos, aVal, aQuery, setter, aFuncParams, aTestStatementFactory);
					if (result) {
						Log.convErr(new Exception(), new Integer(aPos).toString());
						return;
					}
				}
				setParameter(aStmt, aPos, aVal, aOriginalSetter); // try original setter, which will throw exception
			}
		} else {
			setParameter(aStmt, aPos, aVal, aOriginalSetter);
		}
	}
	
	protected static boolean trySingleSettterHeuristically(PreparedStatement aStmt, int aPos, Object aVal, String aQuery, SettersEnum aSetter, FunctionParams aFuncParams, TestStatementFactory aTestStatementFactory) throws SQLException {
		PreparedStatement test;
		
		test = null;
		try {
			test = aTestStatementFactory.createTestStatement(aStmt,aQuery);
			
			if (aStmt instanceof CallableStatement && aFuncParams!=null) {
				for(int i=1; i<=aFuncParams.getParams().size(); i++) {
					if (aFuncParams.getParam(i).getColumnReturn()==4 || aFuncParams.getParam(i).getColumnReturn()==5) {
						((CallableStatement)test).registerOutParameter(i, aFuncParams.getParam(i).getColumnDataType());
					}
				}
			}
			
			if (aVal==null) {
				if (isNull(test, aPos, aSetter.getSqlType())) setNull(aStmt, aPos, aSetter.getSqlType());
				else return false;
				return true;
			}
			
			if (aVal instanceof java.util.Date) {
				long tval = ((java.util.Date)aVal).getTime();
				if (SettersEnum.setDate.equals(aSetter) && isDate(test, aPos, new Date(tval))) setDate(aStmt, aPos, new Date(tval));
				else if (SettersEnum.setTime.equals(aSetter) && isTime(test, aPos, new Time(tval))) setTime(aStmt, aPos, new Time(tval));
				else if (SettersEnum.setTimestamp.equals(aSetter) && isTimestamp(test, aPos, new Timestamp(tval))) setTimestamp(aStmt, aPos, new Timestamp(tval));
				else return false;
				return true;
			} else {
				String sval = aVal.toString();
				if (SettersEnum.setString.equals(aSetter) && isString(test, aPos, sval)) 				setString(aStmt, aPos, sval);
				else if (SettersEnum.setBigDecimal.equals(aSetter) && isBigDecimal(test, aPos, new BigDecimal(sval))) setBigDecimal(aStmt, aPos, new BigDecimal(sval));
				else if (SettersEnum.setByte.equals(aSetter) && isByte(test, aPos, new Byte(sval))) 			setByte(aStmt, aPos, new Byte(sval));
				else if (SettersEnum.setDouble.equals(aSetter) && isDouble(test, aPos, new Double(sval))) 	setDouble(aStmt, aPos, new Double(sval));
				else if (SettersEnum.setFloat.equals(aSetter) && isFloat(test, aPos, new Float(sval))) 		setFloat(aStmt, aPos, new Float(sval));
				else if (SettersEnum.setInt.equals(aSetter) && isInt(test, aPos, new Integer(sval))) 		setInt(aStmt, aPos, new Integer(sval));
				else if (SettersEnum.setLong.equals(aSetter) && isLong(test, aPos, new Long(sval))) 			setLong(aStmt, aPos, new Long(sval));
				else if (SettersEnum.setShort.equals(aSetter) && isShort(test, aPos, new Short(sval))) 		setShort(aStmt, aPos, new Short(sval));
				else if (SettersEnum.setBoolean.equals(aSetter) && isBoolean(test, aPos, new Boolean(sval))) 		setBoolean(aStmt, aPos, new Boolean(sval));
				else if (SettersEnum.setBooleanAsInt.equals(aSetter) && isInt(test, aPos, Boolean.TRUE.equals(new Boolean(sval))?1:0)) 		setInt(aStmt, aPos, Boolean.TRUE.equals(new Boolean(sval))?1:0);
				else return false;
				return true;
			}
		} catch (Exception e) {
		} finally {
			if (test!=null) test.close();
		}
		return false;
	}
	
	protected static void setParameter(PreparedStatement aStmt, int aPos, Object aVal, SettersEnum aSetter) throws SQLException {
		
		if (aVal==null) {
			setNull(aStmt, aPos, aSetter.getSqlType());
			return;
		}
		
		if (aVal instanceof java.util.Date) {
			long tval = ((java.util.Date)aVal).getTime();
			if (SettersEnum.setDate.equals(aSetter))  setDate(aStmt, aPos, new Date(tval));
			if (SettersEnum.setTime.equals(aSetter))  setTime(aStmt, aPos, new Time(tval));
			if (SettersEnum.setTimestamp.equals(aSetter))  setTimestamp(aStmt, aPos, new Timestamp(tval));
		} else {
			String sval = aVal!=null? aVal.toString() : null;
			if (SettersEnum.setString.equals(aSetter)) 				 setString(aStmt, aPos, sval);
			if (SettersEnum.setBigDecimal.equals(aSetter))  setBigDecimal(aStmt, aPos, new BigDecimal(sval));
			if (SettersEnum.setByte.equals(aSetter)) 			 setByte(aStmt, aPos, new Byte(sval));
			if (SettersEnum.setDouble.equals(aSetter)) 	 setDouble(aStmt, aPos, new Double(sval));
			if (SettersEnum.setFloat.equals(aSetter)) 		 setFloat(aStmt, aPos, new Float(sval));
			if (SettersEnum.setInt.equals(aSetter)) 		 setInt(aStmt, aPos, new Integer(sval));
			if (SettersEnum.setLong.equals(aSetter)) 			 setLong(aStmt, aPos, new Long(sval));
			if (SettersEnum.setShort.equals(aSetter)) 		 setShort(aStmt, aPos, new Short(sval));
			if (SettersEnum.setBoolean.equals(aSetter)) 		setBoolean(aStmt, aPos, new Boolean(sval));
		}
		
	}
	
	protected static boolean setString(PreparedStatement aStmt, int aPos, String aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setString(aPos, aVal);
		return true;
	}
	
	protected static boolean setNString(PreparedStatement aStmt, int aPos, String aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setNString(aPos, aVal);
		return true;
	}
	
	protected static boolean setLong(PreparedStatement aStmt, int aPos, Long aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setLong(aPos, aVal);
		return true;
	}
	
	protected static boolean setInt(PreparedStatement aStmt, int aPos, int aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setInt(aPos, aVal);
		return true;
	}
	
	protected static boolean setDouble(PreparedStatement aStmt, int aPos, double aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setDouble(aPos, aVal);
		return true;
	}
	
	protected static boolean setFloat(PreparedStatement aStmt, int aPos, float aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setFloat(aPos, aVal);
		return true;
	}
	
	protected static boolean setTime(PreparedStatement aStmt, int aPos, Time aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setTime(aPos, aVal);
		return true;
	}
	
	protected static boolean setDate(PreparedStatement aStmt, int aPos, Date aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setDate(aPos, aVal);
		return true;
	}
	
	protected static boolean setTimestamp(PreparedStatement aStmt, int aPos, Timestamp aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setTimestamp(aPos, aVal);
		return true;
	}
	
	protected static boolean setBigDecimal(PreparedStatement aStmt, int aPos, BigDecimal aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setBigDecimal(aPos, aVal);
		return true;
	}
	
	protected static boolean setByte(PreparedStatement aStmt, int aPos, byte aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setByte(aPos, aVal);
		return true;
	}
	
	protected static boolean setShort(PreparedStatement aStmt, int aPos, short aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setShort(aPos, aVal);
		return true;
	}
	
	protected static boolean setBoolean(PreparedStatement aStmt, int aPos, boolean aVal/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setBoolean(aPos, aVal);
		return true;
	}
	
	protected static boolean setNull(PreparedStatement aStmt, int aPos, int sqlType/*, PreparedStatement aTest*/) throws SQLException {
		aStmt.setNull(aPos, sqlType);
		return true;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------	
	
	protected static boolean isString(PreparedStatement aTest, int aPos, String aVal) {
		try {
			aTest.setString(aPos, aVal); 
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isNString(PreparedStatement aTest, int aPos, String aVal) {
		try {
			aTest.setNString(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected static boolean isLong(PreparedStatement aTest, int aPos, long aVal) {
		try {
			aTest.setLong(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isInt(PreparedStatement aTest, int aPos, int aVal) {
		try {
			aTest.setInt(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isDouble(PreparedStatement aTest, int aPos, double aVal) {
		try {
			aTest.setDouble(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isFloat(PreparedStatement aTest, int aPos, float aVal) {
		try {
			aTest.setFloat(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isBigDecimal(PreparedStatement aTest, int aPos, BigDecimal aVal) {
		try {
			aTest.setBigDecimal(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isDate(PreparedStatement aTest, int aPos, Date aVal) {
		try {
			aTest.setDate(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isTime(PreparedStatement aTest, int aPos, Time aVal) {
		try {
			aTest.setTime(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isTimestamp(PreparedStatement aTest, int aPos, Timestamp aVal) {
		try {
			aTest.setTimestamp(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isByte(PreparedStatement aTest, int aPos, byte aVal) {
		try {
			aTest.setByte(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isShort(PreparedStatement aTest, int aPos, short aVal) {
		try {
			aTest.setShort(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isBoolean(PreparedStatement aTest, int aPos, boolean aVal) {
		try {
			aTest.setBoolean(aPos, aVal);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected static boolean isNull(PreparedStatement aTest, int aPos, int aSqlType) {
		try {
			aTest.setNull(aPos, aSqlType);
			aTest.getMetaData();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
}

interface TestStatementFactory {
	PreparedStatement createTestStatement(Statement aStmt, String aQuery) throws SQLException;
}