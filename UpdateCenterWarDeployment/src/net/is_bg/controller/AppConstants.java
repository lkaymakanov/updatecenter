package net.is_bg.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.is_bg.updatercenter.common.context.ContextUtils;

public class AppConstants {
	
	//names of the parameters that go in server xml!!!!
	private static final String serverlibdir = "serverlibdir";                 			  //the directory containing the jar libs on the server!!!
	private static final String versionsrootdir = "versionsrootdir";			 			  //the directory containing the versions available for update!!!
	private static final String updatecenterroot = "updatecenterroot";                      //update center root directory
	private static final String versionnumberprefix = "versionnumberprefix";
	private static final String validationpatternpropertyfile = "validationpatternpropertyfile";
	private static final String chuncksize = "chunksize";
	
	
	public static final String WEB_INF_LIBS = "WEB-INF\\lib";
	/**
	   Samples 
	   <Environment name="serverlibdir" value="D:\\updatecenterroot\\libs" type="java.lang.String" override="false"/>
	   <Environment name="versionsrootdir" value="D:\\updatecenterroot\\versions" type="java.lang.String" override="false"/>
	   <Environment name="updatecenterroot" value="D:\\updatecenterroot" type="java.lang.String" override="false"/>
	   <Environment name="versionnumberprefix" value="-1.2-" type="java.lang.String" override="false"/>
	 */
	public enum CONTEXTPARAMS{
			
		UPDATE_CENTER_LIB_DIR(serverlibdir, ContextUtils.getParam(serverlibdir, String.class, ""), String.class),
		UPDATE_CENTER_VERSIONS_DIR(versionsrootdir, ContextUtils.getParam(versionsrootdir, String.class, ""), String.class), 
		UPDATE_CENTER_ROOT(updatecenterroot, ContextUtils.getParam(updatecenterroot, String.class, ""), String.class),
		UPDATE_CENTER_VERSION_NUMBER_PREFIX(versionnumberprefix, ContextUtils.getParam(versionnumberprefix, String.class, ""), String.class),
		UPDATE_CENTER_VALIDATION_PATTERN_PROPERTY_FILE(validationpatternpropertyfile, ContextUtils.getParam(validationpatternpropertyfile, String.class, ""), String.class),
		UPDATE_CENTER_CHUNK_SIZE(chuncksize, ContextUtils.getParam(chuncksize, Integer.class, 5*1024*1024), Integer.class);
		
		<T> CONTEXTPARAMS(String name, T defaultValue,  Class<T> c){
			this.name = name;
			this.clazz = c;
			this.value = defaultValue;
		};
		
		String name;
		Class clazz;
		Object value;
		
		public String getName() {
			return name;
		}
		public Class getClazz() {
			return clazz;
		}
		
		
		public Object getValue(){
			return value;
		}
		
		public static void printParams(){
			CONTEXTPARAMS[] arr = CONTEXTPARAMS.values();
			for(int i =0 ; i< arr.length ; i++){
				System.out.println(arr[i].getName() + "=" + arr[i].getValue());
			}
		}
	}
	
	
	/***
	 * Match each file name in the version directory against the valid patterns!!!
	 * If pattern matches create a version description for that file!!!
	 * @author lubo
	 *
	 */
	public enum VERSION_VALIDATION_PATTERNS{
		PATTERNS,
		VERSION_VALIDATION_PATTERNS(){};
		
		private List<String> patterns = new  ArrayList<String>();
		
		public static void initPropertiesByPropertyFile(String propFile){
			Properties prop =  FileUtil.loadProperties(propFile);
			List<String> p = new  ArrayList<String>();
			
			for(Object o : prop.values()){
				p.add(o.toString());
			}
			
			PATTERNS.patterns = p;
		}
		public List<String> getPatterns() {
			return patterns;
		}
	}
		
	
	
}
