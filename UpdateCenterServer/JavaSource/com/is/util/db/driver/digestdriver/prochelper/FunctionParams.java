package com.is.util.db.driver.digestdriver.prochelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionParams {

	String procedureCatalog;
    String procedureSchema;
    String procedureName;
    
    List<FunctionParam> params = new ArrayList<FunctionParam>();

    public FunctionParams(String procedureCatalog, String procedureSchema,
			String procedureName) {
		super();
		this.procedureCatalog = procedureCatalog;
		this.procedureSchema = procedureSchema;
		this.procedureName = procedureName;
	}
	public List<FunctionParam> getParams() {
		return params;
	}
	public void setParams(List<FunctionParam> params) {
		this.params = params;
	}
	public String getProcedureCatalog() {
		return procedureCatalog;
	}
	public void setProcedureCatalog(String procedureCatalog) {
		this.procedureCatalog = procedureCatalog;
	}
	public String getProcedureSchema() {
		return procedureSchema;
	}
	public void setProcedureSchema(String procedureSchema) {
		this.procedureSchema = procedureSchema;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public void addParameter(FunctionParam param) {
    	params.add(param);
    }
    public FunctionParam getParam(int idx) {
    	return params.get(idx-1);
    }
    public FunctionParam getParam(String name) {
    	for(int i=0; i<params.size(); i++) {
    		if (params.get(i).getColumnName().equals(name)) return params.get(i);
    	}
    	return null;
    }
    public int getParamIndex(String name) {
    	for(int i=0; i<params.size(); i++) {
    		if (params.get(i).getColumnName().equals(name)) return i+1;
    	}
    	return -1;
    }
	
}
