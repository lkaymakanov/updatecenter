package applicationupdate;

public class ClientJavaVersion {

	public static enum JAVA_VERSION{
		JAVA_5("1.5", 5),
		JAVA_6("1.6", 6),
		JAVA_7("1.7", 7),
		JAVA_8("1.8", 8);
		
		private JAVA_VERSION(String s, int number){
			this.version = s;
			this.number = number;
		}
		
		private String version;
		private int number;
		
		
		public String getVersion() {
			return version;
		}

		public int getNumber() {
			return number;
		}

		public static JAVA_VERSION getByString(String version){
			for(JAVA_VERSION v: JAVA_VERSION.values()){
				if(v.version.equals(version)) return v;
			}
			return null;
		}
		
		public static JAVA_VERSION getByNumber(int number){
			for(JAVA_VERSION v: JAVA_VERSION.values()){
				if(v.number == number) return v;
			}
			return null;
		}
		
		public static JAVA_VERSION getClientVersion(){
			String jver = System.getProperty("java.version");
			System.out.println(jver);
			return getByString(jver.substring(0, 3));
			
		}
	}
	
	
}
