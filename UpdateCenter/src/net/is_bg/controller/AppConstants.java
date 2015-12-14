package net.is_bg.controller;


import net.is_bg.updatercenter.common.context.ContextUtils;

public class AppConstants {
	
	//names of the params that go in server xml!!!!
	private static String serverlibdir = "serverlibdir";                 			  //the directory containing the jar libs on the server!!!
	private static String versionsrootdir = "versionsrootdir";			 			  //the directory containing the versions available for update!!!
	private static String updatecenterroot = "updatecenterroot";                      //update center root directory
	public static final String CONTROLLER_PACKAGE = "net.is_bg.controller";           //the package that contains controllers
	
	public static final String VERSION_NUMBER_PREFIX = "-1.2-";
	
	public enum CONTEXTPARAMS{
			
		//task scheduler params
		UPDATE_CENTER_LIB_DIR(serverlibdir, ContextUtils.getParam(serverlibdir, String.class, "D:\\updatecenterroot\\libs"), String.class),
		UPDATE_CENTER_VERSIONS_DIR(versionsrootdir, ContextUtils.getParam(versionsrootdir, String.class, "D:\\updatecenterroot\\versions"), String.class), 
		UPDATE_CENTER_ROOT(updatecenterroot, ContextUtils.getParam(updatecenterroot, String.class, "D:\\updatecenterroot"), String.class);

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
		LTF("[lL][tT][fF]-1.2-(\\d)+.[wW][aA][rR]"),
		ONLINE_REPORT("[Oo][nN][Ll][Ii][Nn][Ee][Rr][Ee][Pp][Oo][Rr][Tt][Ww][Ss][Cc][Ll][Ii][Ee][Nn][Tt]-1.2-(\\d)+.[wW][aA][rR]");
		
		private VERSION_VALIDATION_PATTERNS(String pattern){
			this.pattern = pattern;
		}
		
		private String pattern;
		public String getPattern() {
			return pattern;
		}
	}
		
	
	
}
