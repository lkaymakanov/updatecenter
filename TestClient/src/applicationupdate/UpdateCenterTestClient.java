package applicationupdate;


import java.util.HashMap;
import java.util.Map;


/***
 * @author lubo
 *
 */

public class UpdateCenterTestClient {
	
	
	
	public static void main(String[] args) throws  Exception {
		Map<String, String> keyVal = new HashMap<String, String>();
		
		if(args != null){
			for(int i = 0 ; i < args.length; i++){
				String k = args[i];
				String v = (i + 1) < args.length ? args[++i] : null;
				keyVal.put(k.toLowerCase(), v == null ? null : v);
			}
		}
		
		
		if(keyVal.containsKey("-h") || keyVal.containsKey("/h") || keyVal.containsKey("/help") || keyVal.containsKey("help") || keyVal.containsKey("-help")){
			System.out.println(" -h  prints this help..." );
			System.out.println(" -s  [server ip address] determines server ip address (default is \"localhost\")" );
			System.out.println(" -p  [port number] determines server port (default is 8080)" );
			System.out.println(" -proto  [protocol type] determines the protocol; one of these [http | https] (default is http)" );
			System.out.println(" -app [aplication name lowercase - example ltf]  ");
			System.out.println(" -downloaddir [aplication download dir - if not set current dir is used...]  ");
			System.out.println(" -libdir [aplication download library dir - if not set current dir is used...]  ");
			System.out.println(" -jar [directory path of jar.exe file used to warp application - if not set current dir is used...]");
			System.out.println(" -socketprotocol  one of these [ssl | tls] ");
			System.out.println(" -storetype one of these [jks | pkcs12]  ");
			
			System.out.println(" -keystorefile [the path to the keystorefile]  ");
			System.out.println(" -keystorepass [the password of the keysore]  ");
			System.out.println(" -keyalias [private key alias]  ");
			System.out.println(" -keypass [private key password]  ");
			return; 
		}
		
		String protocol = keyVal.get("-proto")!=null ? keyVal.get("-proto") : "http";
		String server =   keyVal.get("-s")!=null ? keyVal.get("-s") : "10.240.110.181";
		String port =  keyVal.get("-p")!=null ? keyVal.get("-p") : "8080";
		String application =  keyVal.get("-app")!=null ? keyVal.get("-app") : "ltf";
		
		String keystoreFile = keyVal.get("-keystorefile")!=null ? keyVal.get("-keystorefile") : null;
		String keystorePass = keyVal.get("-keystorepass")!=null ? keyVal.get("-keystorepass") : null;
		String keyAlias = keyVal.get("-keyalias")!=null ? keyVal.get("-keyalias") : null;
		String keyPass = keyVal.get("-keypass")!=null ? keyVal.get("-keypass") : null;
		String socketProtocol = keyVal.get("-socketprotocol")!=null ? keyVal.get("-socketprotocol").toLowerCase() : null;
		String storeType = keyVal.get("-storetype")!=null ? keyVal.get("-storetype").toLowerCase() : null;
		
		String downloaddir = keyVal.get("-downloaddir")!=null ? keyVal.get("-downloaddir").toLowerCase() : "versions";
		String libdir = keyVal.get("-libdir")!=null ? keyVal.get("-libdir").toLowerCase() : "libs";
		String jar = keyVal.get("-jar")!=null ? keyVal.get("-jar").toLowerCase() : "";
		
		System.out.println(DownLoadUtils.getParamTable(keyVal));
		
		
		DownloadSettings dSettings = new  DownloadSettings();
		dSettings.setApplication(application);
		dSettings.setDownLoadPathsFiles(new  DownLoadPathsFiles(jar, downloaddir, libdir));
		ServerSettings sSettings = dSettings.getServerSettings();
		sSettings.setPort(port);
		sSettings.setProtocol(protocol);
		sSettings.setIp(server);
		dSettings.setSocketProtocol(DownloadSettings.socketProtocolFromString(socketProtocol));
		dSettings.setStoreType(DownloadSettings.storeTypeFromString(storeType));
		dSettings.setKeyAlias(keyAlias);
		dSettings.setKeyPass(keyPass);
		dSettings.setKeystoreFile(keystoreFile);
		dSettings.setKeystorePass(keystorePass);
		
		DownloadVersion updater = new DownloadVersion(dSettings);
		updater.download();
		
		
	}
	
}
	
