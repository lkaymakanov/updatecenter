package applicationupdate;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Set;

import net.is_bg.updatercenter.common.Enumerators;

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
			privateKey(dSettings.getKeyAlias(), dSettings.getKeyPass());
		}else{
			ssl.noSSL().complete();
		}
	}
	
	
	

	
}
