package net.is_bg.updatercenter.common.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VersionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2234840071542417791L;
	public String fileName;
	public long number;
	public long chunksNumber;
	
	
	//libraries required by the application
	public List<String> libs = new  ArrayList<String>();
	
	//application script files
	public List<String> scripts = new ArrayList<>();
	
	/***
	 * The Crc 32 bit hash code of the version war file!!!!
	 */
	public long crc32;
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder bd = new  StringBuilder();
		bd.append("=========== Version Info table =========\n");
		bd.append("fileName:"+fileName+"\n");
		bd.append("version number :"+number+"\n");
		bd.append("chunksNumber:"+chunksNumber+"\n");
		bd.append("\n");
		bd.append("Required libraries..."+"\n");
		for ( String l : libs){
			bd.append(l +"\n");
		}
		bd.append("\n");
		bd.append("Version Crc: " + crc32);
		bd.append("\n");
		return bd.toString();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
