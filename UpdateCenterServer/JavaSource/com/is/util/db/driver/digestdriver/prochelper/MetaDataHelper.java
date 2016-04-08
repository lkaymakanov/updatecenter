package com.is.util.db.driver.digestdriver.prochelper;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MetaDataHelper {

	public static FunctionParams getFunctionParams(Statement st, String schema, String procname, boolean hasReturnParam) throws SQLException {
	   
		FunctionParams result = null; 
		
	    DatabaseMetaData dbMetaData = st.getConnection().getMetaData();
	    ResultSet rs = dbMetaData.getProcedureColumns(st.getConnection().getCatalog(),null,null,null);
	    
	    while(rs.next()) {
	      // get stored procedure metadata
	      String procedureCatalog     = rs.getString(1);
	      String procedureSchema      = rs.getString(2);
	      String procedureName        = rs.getString(3);
	      String columnName           = rs.getString(4);
	      short  columnReturn         = rs.getShort(5);
	      int    columnDataType       = rs.getInt(6);
	      String columnReturnTypeName = rs.getString(7);
	      int    columnPrecision      = rs.getInt(8);
	      int    columnByteLength     = rs.getInt(9);
	      short  columnScale          = rs.getShort(10);
	      short  columnRadix          = rs.getShort(11);
	      short  columnNullable       = rs.getShort(12);
	      String columnRemarks        = rs.getString(13);

	      if ((schema==null || schema.equalsIgnoreCase(procedureSchema)) && (procedureName.equalsIgnoreCase(procname))) {
		      if (result==null) result=new FunctionParams(procedureCatalog, procedureSchema, procedureName);
		      
		      if (!(!hasReturnParam && columnReturn==5)) {
			      FunctionParam fp = new FunctionParam(columnName, columnReturn, columnDataType, columnReturnTypeName, columnPrecision, columnByteLength, columnScale, columnRadix, columnNullable, columnRemarks);
			      result.addParameter(fp);
		      }
	      }
	    }
	    
	    return result;
	}
	
	
	
}
