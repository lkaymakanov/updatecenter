package net.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.is_bg.updatercenter.common.AppConstants;
import net.is_bg.updatercenter.common.Enumerators;
import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.Enumerators.PARAMS;
import net.is_bg.updatercenter.common.FileData;
import net.is_bg.updatercenter.common.RequestParams;
import net.is_bg.updatercenter.common.crc.Crc;
import net.is_bg.updatercenter.common.resources.Session;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import net.is_bg.updatercenter.common.zippack.Packager;

import com.cc.rest.client.ClientConfigurator;
import com.cc.rest.client.Requester;
import com.cc.rest.client.Requester.MEDIA_TYPE;
import com.cc.rest.client.enumerators.IREST_PATH;

import file.splitter.ByteChunk;

public class VersionUpdater {
	
	//names of the params that go in server xml!!!!
	private static String downloadDir = "downloaddir"; 		  //list with comma separated jar files
	//private static String unzipappdir = "unzipappdir";
	private static String downloadLibDir = "downloadlibdir"; 	
	
	private static String getCurrentfileDir(){
		try {
			return new File(".").getCanonicalFile().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public enum CONTEXTPARAMS{
		
		//task scheduler params
		DOWNLOAD_ROOT_DIR(downloadDir, getCurrentfileDir(), String.class),
		DOWNLOAD_LIB_DIR(downloadLibDir, getCurrentfileDir() + File.separator + "lib", String.class);
		//UNZIP_APP_DIR(unzipappdir, getCurrentfileDir() + File.separator + "unzipappdir", String.class);
		

		<T> CONTEXTPARAMS(String name, T defaultValue,  Class<T> c){
			this.name = name;
			this.clazz = c;
			this.value = defaultValue;
		};
		
		String name;
		@SuppressWarnings("rawtypes")
		Class clazz;
		Object value;
		
		public String getName() {
			return name;
		}
		@SuppressWarnings("rawtypes")
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
	
	private String currentSessionId = "-1";
	private String protocol = "http";
	private String serverIp = "localhost";
	private String port = "8080";
	private String context= "UpdateCenterServer";
	private String endpoint = protocol+"://" + serverIp + ":" + port + "/" + context;
	private String appName = "";
	private String sessionid = "1234";
	
	/**The main path pointing to update center jersey servlet*/
	private final static IREST_PATH MAIN_PATH = new  IREST_PATH() {
		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return "/updates" + AppConstants.PATH_APPLICATION;
		}
	}; 
	
	public VersionUpdater() throws Exception{
		this("http", "localhost", "8080", "LTF", null);
	}
	
	public VersionUpdater(String protocol, String serverIp, String port, String application, RequestParams params) throws Exception{
		this.protocol = protocol;
		this.serverIp = serverIp;
		this.port = port;
		endpoint = protocol+"://" + serverIp + ":" + port + "/" + context;
		ClientConfigurator.configure().targetEndpoint(endpoint).readTimeout(600).noSSL().complete();
		
		//get the names of the supported versions
		Set<String> vnames = getVersionsNames();
		application = application.toLowerCase();
		for(String s : vnames){
			if(s.toLowerCase().contains(application)){   //check if name is in the the set of supported applications
				appName = net.is_bg.updatercenter.common.FileUtil.removeFileExtension(s);
				break;
			}
		}
		//sessionId
		sessionid = getSession(params).getSessionId();
	}
	
	private  VersionInfo getVersionInfo() throws  Exception {
		VersionInfo v = Requester.request().path(MAIN_PATH)
				.subPath(Enumerators.getVersionSubPath(appName)).queryParam(PARAMS.SESSION_ID, sessionid)
				.get(MEDIA_TYPE.JSON).getResponseObject(VersionInfo.class);
		return v;
	}
	
	private Session getSession(RequestParams params) throws  Exception {
		Session session = Requester.request().path(MAIN_PATH)
				.subPath(Enumerators.getCreateSessionSubPath(appName))
				.get(MEDIA_TYPE.JSON)
				.getResponseObject(Session.class);
		return session;
	}
	
	
	private Set<String>  getVersionsNames() throws Exception{
		@SuppressWarnings("unchecked")
		Set<String> s = Requester.request().path(MAIN_PATH)
				.subPath(Enumerators.getVersionNamesSubPath())
				.get(MEDIA_TYPE.JSON)
				.getResponseObject(Set.class);
		return s;
	}

	/**
	 * Retrieves a file from update server by fileName - used to download the application files!!!
	 * @param fileName
	 * @param sessionId
	 * @return
	 * @throws Exception 
	 * @throws JsonProcessingException
	 */
	private byte []  getFileByFileName(String fileName, String sessionId) throws  Exception {
		FileData d =  Requester.request()
				.path(MAIN_PATH)
				.subPath(Enumerators.getFileSubPath(appName, fileName))
				.queryParam(PARAMS.SESSION_ID,  sessionid)
				.get(MEDIA_TYPE.JSON)
				.getResponseObject(FileData.class);
		
		return d.getBytes();
	}
	

	public void update() throws  Exception {
		// TODO Auto-generated method stub
		//get the last version from the server & the pieces it is split into
		VersionInfo v = getVersionInfo();
		System.out.println(v);
		
		
		
		//create directory with name - the versions number
		String versionDir = CONTEXTPARAMS.DOWNLOAD_ROOT_DIR.getValue() + File.separator + v.getNumber();
		FileUtil.createDirIfNotExist(versionDir);

		
		//save received buffers to a file at the client side...
		String zipVersionFile = versionDir + File.separator +  v.getFileName() +"nolib";
		File res = new File(zipVersionFile);
		OutputStream os = new FileOutputStream(res);
		int i = 0;
		ByteChunk b = new ByteChunk();
		while(i < v.getChunksNumber()){
		    b.buffer =  getFileByFileName(String.valueOf(i), currentSessionId);
		    b.size = b.buffer.length;
			System.out.println("Byte Chunk number " +  i + " with size " + b.size + " Bytes received successfuly" );
			os.write(b.buffer, 0, b.size);
			Thread.sleep(2000);
			i++;
		}
		os.close();
		os.flush();
		
		
		//create lib dir if not exists
		File libdir =  new File(CONTEXTPARAMS.DOWNLOAD_LIB_DIR.getValue().toString());
		if(!libdir.exists()) libdir.mkdir();
		
		//download the necessary  lib files
		for(String s:v.libs){
			res = new File(CONTEXTPARAMS.DOWNLOAD_LIB_DIR.getValue() + File.separator +  s);
			if(res.exists()){  continue; }
		    
			b.buffer = getFileByFileName(s, currentSessionId);
			b.size = b.buffer.length;
		    //save the received buffer to file in lib dir!!!
			os = new FileOutputStream(res);
			os.write(b.buffer, 0, b.size);
			os.close();
			os.flush();
			Thread.sleep(2000);
			System.out.println( "File " + s+"  with size " + b.size + " Bytes received successfuly" );
		}
		
		
		//create unzip app dir if not exists
		String unzipDir = versionDir + File.separator + "unzipappdir";
		FileUtil.createDirIfNotExist(unzipDir);
		
		
		//unzip the application without the libs
		System.out.println("Unzipping " + zipVersionFile + " to " +  unzipDir);
		Packager.unZipIt(zipVersionFile, unzipDir);
		
		//copy the libraries into the application lib dir!!!
		String applicationlibPath = unzipDir + File.separator + "WEB-INF" + File.separator + "lib";
		File applib = new File(applicationlibPath);
		if(!applib.exists()) applib.mkdir();
		System.out.println("Copying lib files  from " + libdir  + " to " + applicationlibPath);
		FileUtil.copyDirectory(new File(CONTEXTPARAMS.DOWNLOAD_LIB_DIR.getValue().toString()), applib);
		
		//zip back into the original version war file
		List<File> f = new ArrayList<File>();
		String subFilesAndFolders []  = new File(unzipDir).list();
		for(String s : subFilesAndFolders){
			f.add(new File(unzipDir + File.separator + s) );
		}
		String downloadedVersionFile = versionDir + File.separator + v.getFileName();
		Packager.packZip(new File(downloadedVersionFile), f);
		
			
		//delete the .warnolib file
		res = new File(zipVersionFile);
		res.delete();
		
		//calculate the crc 32 of  the received file & compare it to received crc 32 hash of the version info
		long receivedFileCrc32 = Crc.checksumZip(downloadedVersionFile);
		System.out.println("Original " + v.getFileName() + " crc32 is " + v.crc32);
		System.out.println("Received " + v.getFileName() + " crc32 is " + receivedFileCrc32);
		
	}
	
	
}
