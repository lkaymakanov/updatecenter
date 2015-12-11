package net.is_bg.controller;


import net.is_bg.updatercenter.common.context.ContextUtils;

public class AppConstants {
	
	//names of the params that go in server xml!!!!
	private static String serverlibdir = "serverlibdir"; 		  //list with comma separated jar files
	private static String ltfscriptdir = "ltfscriptdir";   		  //list with comma separated script files
	private static String ltfwar = "ltfwar";                	  //the war file name!!!
	private static String ltfupadterootdir = "ltfupadterootdir";  //the war file directory!!!
	private static String ltfscriptfile = "ltfscriptfile";
	private static String deploy = "deploy";
	
	
	public enum CONTEXTPARAMS{
			
		//task scheduler params
		SERVER_LIB_DIR(serverlibdir, ContextUtils.getParam(serverlibdir, String.class, "D:\\updatecenterroot\\libs"), String.class),
		LTF_UPDATE_ROOT_DIR(ltfupadterootdir, ContextUtils.getParam(serverlibdir, String.class, "D:\\updatecenterroot\\ltf"), String.class),
		LTF_SCRIPT_DIR(ltfscriptdir,  ContextUtils.getParam(ltfscriptdir, String.class, "D:\\updatecenterroot"),  String.class),
		LTF_WAR_FILE(ltfwar, ContextUtils.getParam(ltfwar, String.class, "LTF-1.2-6866.war"), String.class),
		LTF_DEPLOY(deploy, ContextUtils.getParam(ltfwar, Boolean.class, true), Boolean.class),
		LTF_SCRIPT_FILE(ltfscriptfile,  ContextUtils.getParam(ltfwar, String.class, "scripts-6866.sql"), String.class),
		
		ONLINE_REPORT_WAR_FILE("onlinereportwar", ContextUtils.getParam("onlinereportwar", String.class, "OnlineReportWSClient-1.2-1111.war"), String.class),
		ONLINE_REPORT_ROOT_DIR("onlinereportrootdir", ContextUtils.getParam("onlinereportrootdir", String.class, "D:\\updatecenterroot\\onlinereport"), String.class);
		

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
		

	
}
