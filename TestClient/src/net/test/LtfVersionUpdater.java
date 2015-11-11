package net.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.updatercenter.common.Enumerators.PARAMS;
import net.is_bg.updatercenter.common.Enumerators.REST_PATH;
import net.is_bg.updatercenter.common.crc.Crc;
import net.is_bg.updatercenter.common.resources.Session;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import net.is_bg.updatercenter.common.zippack.Packager;

import org.codehaus.jackson.JsonProcessingException;

import com.cc.rest.client.Requester;
import com.cc.rest.client.Requester.ACCEPT_TYPE;

import file.splitter.ByteChunk;

public class LtfVersionUpdater {
	
	//names of the params that go in server xml!!!!
	private static String downloadDir = "downloaddir"; 		  //list with comma separated jar files
	private static String unzipappdir = "unzipappdir";
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
		DOWNLOAD_LIB_DIR(downloadLibDir, getCurrentfileDir() + File.separator + "lib", String.class),
		UNZIP_APP_DIR(unzipappdir, getCurrentfileDir() + File.separator + "unzipappdir", String.class);
		

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
	
	private String currentSessionId = "-1";
	private String protocol = "http";
	private String serverIp = "localhost";
	private String port = "8080";
	private String context= "UpdateCenter";
	private String endpoint = protocol+"://" + serverIp + ":" + port + "/" + context;
	
	public LtfVersionUpdater(){
		this("http", "localhost", "8080");
	}
	
	public LtfVersionUpdater(String protocol, String serverIp, String port){
		this.protocol = protocol;
		this.serverIp = serverIp;
		this.port = port;
		endpoint = protocol+"://" + serverIp + ":" + port + "/" + context;
	}
	
	private VersionInfo getVersionInfo() throws JsonProcessingException, IOException {
		VersionInfo v = Requester.create()
				.endpoint(endpoint)
				.path(REST_PATH.LTF)
				.subPath(REST_PATH.CURRENT_VERSION)
				.get(ACCEPT_TYPE.JSON)
				.getResponseObject(VersionInfo.class);
		
		return v;
	}
	
	private Session getSession() throws JsonProcessingException, IOException {
		Session session = Requester.create()
				.endpoint(endpoint)
				.path(REST_PATH.LTF)
				.subPath(REST_PATH.GET_SESSION)
				.get(ACCEPT_TYPE.JSON)
				.getResponseObject(Session.class);
		return session;
	}
	
	
	private ByteChunk getChunkNo(long chunkNo, String sessionId) throws JsonProcessingException, IOException {
		return Requester.create()
				.endpoint(endpoint)
				.path(REST_PATH.LTF)
				.subPath(REST_PATH.GET_CHUNK_NO)
				.pathParam(chunkNo)
				.setParam(PARAMS.SESSION_ID, sessionId)
				.get(ACCEPT_TYPE.JSON)
				.getResponseObject(ByteChunk.class);
	}
	
	/**
	 * Retrieves a file from update server by fileName - used to download the application  libs!!!
	 * @param fileName
	 * @param sessionId
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private ByteChunk getFileByFileName(String fileName, String sessionId) throws JsonProcessingException, IOException {
		return Requester.create()
				.endpoint(endpoint)
				.path(REST_PATH.LTF)
				.subPath(REST_PATH.GET_FILE)
				.setParam(PARAMS.FILE_NAME, fileName)
				.setParam(PARAMS.SESSION_ID, sessionId)
				.get(ACCEPT_TYPE.JSON)
				.getResponseObject(ByteChunk.class);
	}
	
	/**
	 * Check if the input sessionId is still active!!!
	 * @param sessionId
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private boolean isSessionActive(String sessionId) throws JsonProcessingException, IOException{
		return Requester.create()
				.endpoint(endpoint)
				.path(REST_PATH.LTF)
				.subPath(REST_PATH.IS_SESSION_ACTIVE)
				.setParam(PARAMS.SESSION_ID, sessionId)
				.get(ACCEPT_TYPE.JSON)
				.getResponseObject(Boolean.class);
	}

	public void update() throws JsonProcessingException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		//get the last version from the server & the pieces it is split into
		VersionInfo v = getVersionInfo();
		System.out.println(v);
		
		//get session first
		if(!isSessionActive(currentSessionId)){
			Session session = getSession();
			currentSessionId = session.getSessionId();
		}
		
		//save received buffers to a file at the client side...
		String zipVersionFile = CONTEXTPARAMS.DOWNLOAD_ROOT_DIR.getValue() + File.separator +  v.fileName +"nolib";
		File res = new File(zipVersionFile);
		OutputStream os = new FileOutputStream(res);
		int i = 0;
		ByteChunk b;
		while(i < v.chunksNumber){
		    b = getChunkNo(i, currentSessionId);
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
		    
			b = getFileByFileName(s, currentSessionId);
		    //save the received buffer to file in lib dir!!!
			os = new FileOutputStream(res);
			os.write(b.buffer, 0, b.size);
			os.close();
			os.flush();
			Thread.sleep(2000);
			System.out.println( "File " + s+"  with size " + b.size + " Bytes received successfuly" );
		}
		
		
		//create unzip app dir if not exists
		String unzipDir = CONTEXTPARAMS.UNZIP_APP_DIR.getValue().toString();
		res = new File(unzipDir);
		if(!res.exists()) res.mkdir();
		
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
		String downloadedVersionFile = CONTEXTPARAMS.DOWNLOAD_ROOT_DIR.getValue() + File.separator + v.fileName;
		Packager.packZip(new File(downloadedVersionFile), f);
		
			
		//delete the .warnolib file
		res = new File(zipVersionFile);
		res.delete();
		
		//calculate the crc 32 of  the received file & compare it to received crc 32 hash of the version info
		long receivedFileCrc32 = Crc.checksumZip(downloadedVersionFile);
		System.out.println("Original " + v.fileName + " crc32 is " + v.crc32);
		System.out.println("Received " + v.fileName + " crc32 is " + receivedFileCrc32);
		
	}
	
	
}
