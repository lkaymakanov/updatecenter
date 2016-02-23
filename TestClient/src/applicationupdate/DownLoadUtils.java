package applicationupdate;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.is_bg.updatercenter.common.Enumerators;
import net.is_bg.updatercenter.common.Ping;

import com.cc.rest.client.ClientConfigurator;
import com.cc.rest.client.ClientConfigurator.Ssl;
import com.cc.rest.client.Requester;
import com.cc.rest.client.Requester.MEDIA_TYPE;

public class DownLoadUtils {

	
	
	/**
	 *  Get supported versions by server!!
	 * @param sSettings - protocol , ip , port!!!
	 * @return
	 * @throws Exception
	 */
	public static Set<String>  getVersionsNames(DownloadSettings dSettings) throws Exception{
		try{
			String cfName = dSettings.getServerSettings().toClientConfigurationName();
			//if(!lock.lock()){
				configureClientConfigurator(dSettings);
				@SuppressWarnings("unchecked")
				Set<String> s = Requester.request(cfName).path(DownloadVersion.MAIN_PATH)
						.subPath(Enumerators.getVersionNamesSubPath())
						.get(MEDIA_TYPE.JSON)
						.getResponseObject(Set.class);
				return s;
			}
		finally{
			//lock.unLock();
		}
	}
	
	public static boolean ping(DownloadSettings dSettings){
		try{
			String cfName = dSettings.getServerSettings().toClientConfigurationName();
			configureClientConfigurator(dSettings);
			@SuppressWarnings("unchecked")
			Ping p = Requester.request(cfName).path(DownloadVersion.MAIN_PATH)
					.subPath(Enumerators.getPingSubPath())
					.get(MEDIA_TYPE.JSON)
					.getResponseObject(Ping.class);
			return p!=null;
		}catch(Exception e){
			return false;
		}
	}
	
	
	public static String getParamTable(Map<String, String> params){
		int tblwidth = 70;
		char tblCorner = '+';
		char tblSide = '|';
		StringBuilder bd = new  StringBuilder();
		bd.append(tblCorner + padJustify("PARAM TABLE", "=", tblwidth) + tblCorner); bd.append("\n");
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		for(String s : params.keySet()){
			bd.append(tblSide + padJustify(s + "=" +params.get(s), " ", tblwidth) + tblSide);bd.append("\n");
		}
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		bd.append(tblCorner + padJustify("END  PARAM TABLE", "=", tblwidth) + tblCorner);bd.append("\n");
		bd.append("\n");
		return bd.toString();
	}
	
	public static String toPrintTable(List<String> lines, String title, int tblwidth ){
		char tblCorner = '+';
		char tblSide = '|';
		StringBuilder bd = new  StringBuilder();
		bd.append(tblCorner + padJustify(title, "=", tblwidth) + tblCorner); bd.append("\n");
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		for(String s : lines){
			bd.append(tblSide + padJustify(s , " ", tblwidth) + tblSide);bd.append("\n");
		}
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		bd.append(tblCorner + padJustify("==", "=", tblwidth) + tblCorner);bd.append("\n");
		bd.append("\n");
		return bd.toString();
	}
	
	public static String toPrintTable(String line, String title, int tblwidth){
		char tblCorner = '+';
		char tblSide = '|';
		StringBuilder bd = new  StringBuilder();
		bd.append(tblCorner + padJustify(title, "=", tblwidth) + tblCorner); bd.append("\n");
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		bd.append(tblSide + padJustify(line, " ", tblwidth) + tblSide);bd.append("\n");
		bd.append(tblSide + padJustify("            ", " ", tblwidth) + tblSide);bd.append("\n");
		bd.append(tblCorner + padJustify("==", "=", tblwidth) + tblCorner);bd.append("\n");
		bd.append("\n");
		return bd.toString();
	}
	
	public static String singleHeaderLine( String title, int tblwidth){
		char tblCorner = '+';
		StringBuilder bd = new  StringBuilder();
		bd.append(tblCorner + padJustify(title, "=", tblwidth) + tblCorner); bd.append("\n");
		bd.append("\n");
		return bd.toString();
	}
	
	
	public static String padJustify(String s, String symbol, int upto){
		if(s == null || symbol == null) return null;
		int l = s.length();
		if(upto <= l) return s;
		int padleft = (upto - l)/2;
		return 	getPadding(getPadding(s, symbol, padleft + l, true), symbol, upto, false);
	}
	
	
	/**Padding up to fixed length with intervals or zeroes in front of string or after string.*/
	public static String getPadding(String st, String paddingSymbol, int upto, boolean left){
		//StringBuilder sb = new StringBuilder(st);
		
		if(st != null && upto >= st.length()){
			int diff = upto - st.length();
			for(int i = 0; i < diff; i ++ ){
				if(left)  st = paddingSymbol + st;
				else      st = st + paddingSymbol;
			}
		}
		return st;
	}
	
	
	/***
	 * Configures the targeted end point by the download settings!!!
	 * @param sSettings
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 */
	public static void configureClientConfigurator(DownloadSettings dSettings) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException{
		ServerSettings sSettings = dSettings.getServerSettings();
		Ssl ssl =  ClientConfigurator.configure(sSettings.toClientConfigurationName()).targetEndpoint(sSettings.toEndPoint()).readTimeout(dSettings.getReadTimeOut());
		if(sSettings.isSecure()){
			ssl.protocol(dSettings.getSocketProtocol()).
			keystore(dSettings.getStoreType(), dSettings.getKeystoreFile(), dSettings.getKeystorePass()).
			privateKey(dSettings.getKeyAlias(), dSettings.getKeyPass()).trustAllCerts().complete();
		}else{
			ssl.noSSL();
		}
	
	}

	
}
