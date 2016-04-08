package com.is.util.db.driver.digestdriver.prochelper;
import java.sql.ResultSetMetaData;

public class ParametersHelper {

	//compares type expected by getter or setter & compares to the real type of resultset column
	public static void checkRealParameterTypeAndExpactedType(ResultSetMetaData metadata, Object val, int pos){
		   if(metadata  == null ||   val == null) return;
		   try
		   {
			   String typeClass = metadata.getColumnClassName(pos);
			   String valClass = val.getClass().getName();
			   
			   System.out.println("Type class name =  " + typeClass);
			   System.out.println("Val  class name = " + valClass);
			   
			   //different types
			   if(!typeClass.equals(valClass)){
				    System.err.println("DIGEST DRIVER MISMATCHING PARAMETER TYPES==========================================================================");
				    System.err.println("COLUMMN TYPE is  " + typeClass + "  PARAMETER TYPE is  " + valClass);
				    System.out.println("COLUMN NUMBER = " + pos);
					System.out.println("column class name = " + metadata.getColumnClassName(pos));
					System.out.println("column display size =  " +  metadata.getColumnDisplaySize(pos));
					System.out.println("column label = " +  metadata.getColumnLabel(pos));
					System.out.println("column name = " +  metadata.getColumnName(pos));
					System.out.println("column type = " +  metadata.getColumnType(pos));
					System.out.println("column type name  = " +  metadata.getColumnTypeName(pos));
					System.out.println("presicion =  " +  metadata.getPrecision(pos));
					System.out.println("scale =  " +  metadata.getScale(pos));
					System.out.println("schema name = " +  metadata.getSchemaName(pos));
					System.out.println("table name = " +  metadata.getTableName(pos));
			   }
		   }catch (Exception e) {
			// TODO: handle exception
			   System.err.println("exception ocurred in checkRealParameterTypeAndExpactedType.....");
		   }
		
	}
	
	//remove meaningless zero of Bigdecimal & double fields
	public static Object eraseMeaninglessZeroes(Object val){
		if(val == null) return null;
		
		return val;
	}
	
	
	
}
