package com.is.util.db.driver.digestdriver;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public enum SettersEnum {

	setBigDecimal("setBigDecimal", BigDecimal.class,   Types.NUMERIC),
	setByte("setByte", Byte.class, Types.TINYINT),
	setDate("setDate", Date.class, Types.DATE),
	setDouble("setDouble", Double.class, Types.DOUBLE),
	setFloat("setFloat", Float.class, Types.FLOAT),
	setInt("setInt", Integer.class, Types.INTEGER),
	setLong("setLong", Long.class, Types.INTEGER),
	setShort("setShort", Short.class, Types.SMALLINT),
	setString("setString", String.class, Types.VARCHAR),
	setTime("setTime", Time.class, Types.TIME),
	setTimestamp("setTimestamp", Timestamp.class, Types.TIMESTAMP),
	setBoolean("setBoolean", Boolean.class, Types.BOOLEAN), 
	setBooleanAsInt("setInt", Boolean.class, Types.INTEGER),
	setObject("setObject", Object.class, Types.OTHER);
	
	private final String mSetterName;
	private final Class mParameterType;
	private final int	mSqlType;
	
	SettersEnum(String aSetterName, Class aParameterType, int aSqlType) {
		mSetterName = aSetterName;
		mParameterType = aParameterType;
		mSqlType = aSqlType;
		AllSetters.mAllSetters.add(this);
	}
	
	public String getSetterName() {
		return mSetterName;
	}
	
	public int getSqlType() {
		return mSqlType;
	}
	
	public static SettersEnum findSetter(String aSetterName) {
		for(Iterator<SettersEnum> i = iterator(); i.hasNext();) {
			SettersEnum setter = i.next();
			if (setter.getSetterName().equals(aSetterName)) return setter;
		}
		return null;
	}
	
	public static Iterator<SettersEnum> iterator() {
		return AllSetters.mAllSetters.iterator();
	}

	public static SettersEnum getByParameterType(Class aParamType) {
		for(SettersEnum setter : AllSetters.mAllSetters) {
			if (aParamType.equals(setter.mParameterType)) return setter;
		}
		return null;
	}
	
}

class AllSetters {
	final static Set<SettersEnum> mAllSetters = new HashSet<SettersEnum>();
}