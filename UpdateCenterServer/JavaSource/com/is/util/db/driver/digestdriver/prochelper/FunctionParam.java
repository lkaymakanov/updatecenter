package com.is.util.db.driver.digestdriver.prochelper;

public class FunctionParam {

    String columnName;
    short  columnReturn;
    int    columnDataType;
    String columnReturnTypeName;
    int    columnPrecision;
    int    columnByteLength;
    short  columnScale;
    short  columnRadix;
    short  columnNullable;
    String columnRemarks;

    public FunctionParam(String columnName, short columnReturn,
			int columnDataType, String columnReturnTypeName,
			int columnPrecision, int columnByteLength, short columnScale,
			short columnRadix, short columnNullable, String columnRemarks) {
		super();
		this.columnName = columnName;
		this.columnReturn = columnReturn;
		this.columnDataType = columnDataType;
		this.columnReturnTypeName = columnReturnTypeName;
		this.columnPrecision = columnPrecision;
		this.columnByteLength = columnByteLength;
		this.columnScale = columnScale;
		this.columnRadix = columnRadix;
		this.columnNullable = columnNullable;
		this.columnRemarks = columnRemarks;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public short getColumnReturn() {
		return columnReturn;
	}
	public void setColumnReturn(short columnReturn) {
		this.columnReturn = columnReturn;
	}
	public int getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(int columnDataType) {
		this.columnDataType = columnDataType;
	}
	public String getColumnReturnTypeName() {
		return columnReturnTypeName;
	}
	public void setColumnReturnTypeName(String columnReturnTypeName) {
		this.columnReturnTypeName = columnReturnTypeName;
	}
	public int getColumnPrecision() {
		return columnPrecision;
	}
	public void setColumnPrecision(int columnPrecision) {
		this.columnPrecision = columnPrecision;
	}
	public int getColumnByteLength() {
		return columnByteLength;
	}
	public void setColumnByteLength(int columnByteLength) {
		this.columnByteLength = columnByteLength;
	}
	public short getColumnScale() {
		return columnScale;
	}
	public void setColumnScale(short columnScale) {
		this.columnScale = columnScale;
	}
	public short getColumnRadix() {
		return columnRadix;
	}
	public void setColumnRadix(short columnRadix) {
		this.columnRadix = columnRadix;
	}
	public short getColumnNullable() {
		return columnNullable;
	}
	public void setColumnNullable(short columnNullable) {
		this.columnNullable = columnNullable;
	}
	public String getColumnRemarks() {
		return columnRemarks;
	}
	public void setColumnRemarks(String columnRemarks) {
		this.columnRemarks = columnRemarks;
	}
    
}
