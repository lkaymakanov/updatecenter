package net.test;


import java.util.HashMap;
import java.util.Map;


public class Test {
	
	

	public static void main(String[] args) throws  Exception {
		Map<String, String> keyVal = new HashMap<String, String>();
		
		if(args != null){
			for(int i = 0 ; i < args.length; i++){
				String k = args[i];
				String v = (i + 1) < args.length ? args[++i] : null;
				keyVal.put(k.toLowerCase(), v == null ? null : v.toLowerCase());
			}
		}
		
		if(keyVal.containsKey("-h") || keyVal.containsKey("/h") || keyVal.containsKey("/help") || keyVal.containsKey("help") || keyVal.containsKey("-help")){
			System.out.println(" -h  prints this help..." );
			System.out.println(" -s  [server ip address] determines server ip address (default is \"localhost\")" );
			System.out.println(" -p  [port number] determines server port (default is 8080)" );
			System.out.println(" -proto  [protocol type] determines the protocol one of these [http | https] (default is http)" );
			System.out.println(" -app [aplication name lowercase - example ltf]  ");
			return; 
		}
		
		String protocol = keyVal.get("-proto")!=null ? keyVal.get("-proto") : "http";
		String server =   keyVal.get("-s")!=null ? keyVal.get("-s") : "localhost";
		String port =  keyVal.get("-p")!=null ? keyVal.get("-p") : "8080";
		String application =  keyVal.get("-app")!=null ? keyVal.get("-app") : "onlinereportwsclient";
		
		VersionUpdater updater = new VersionUpdater(protocol, server, port, application, null);
		updater.update();
	}
	
}
	
