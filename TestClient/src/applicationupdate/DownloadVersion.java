package applicationupdate;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



//import net.is_bg.ltf.applicationscriptmanagement.FindResource;
//import net.is_bg.ltf.db.common.impl.logging.LogSystemOut;
//import net.is_bg.ltf.db.common.interfaces.logging.ILog;
import net.is_bg.updatercenter.common.AppConstants;
import net.is_bg.updatercenter.common.Enumerators;
import net.is_bg.updatercenter.common.FileData;
import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.RequestParams;
import net.is_bg.updatercenter.common.Enumerators.PARAMS;
import net.is_bg.updatercenter.common.crc.Crc;
import net.is_bg.updatercenter.common.resources.Session;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import net.is_bg.updatercenter.common.zippack.Packager;
import applicationupdate.ClientJavaVersion.JAVA_VERSION;

import com.cc.rest.client.Requester;
import com.cc.rest.client.Requester.MEDIA_TYPE;
import com.cc.rest.client.enumerators.IREST_PATH;

import file.splitter.ByteChunk;


/**
 * <pre>
 * 2.Download version
	- determine if new version is available
	- retrieve the version description 
	- download version chunks
	- determine necessary libraries
	- download necessary libraries
	- create war file


3.Build up version
	- merge the file chunks - this must be a zip file of the new version without the libraries !!!
	- unzip the result file!!!
	- copy the needed libraries (the ones found in the description of version) into $DOWNLOAD_DIR//LTF/WEB-INF/lib  folder !!!
	- zip the application folder again &  create the WAR file !!!
 * @author lubo
 *</pre>
 */
class DownloadVersion  {
	
	//environment variable names  used by packwar.bat file
	private final static String PATH_TO_UZIPPED_APP = "PATH_TO_UZIPPED_APP";
	private final static String PATH = "Path";
	private final static String WAR_FILE = "WAR_FILE";
	private final static String BATCH_FILE_NAME = "packwar.bat";   //the name of the batch file used to make the war file!!!
	
	//path to the jar file & packwar script batch file
	private  String  PATH_TO_JAR;
	
	private String appName = "";
	private String sessionid = "-1";
	private VersionInfo versionInfo;
	private String zipVersionFile, versionDir, unzipDir;
	private File libdir, res;
	private String cfName;
	private RequestParams requestParams = new RequestParams();
	private DownLoadPathsFiles paths;
	
	//private ILog log = new  LogSystemOut();
	
	/**The main path pointing to update center jersey servlet*/
	public final static IREST_PATH MAIN_PATH = new  IREST_PATH() {
		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return "/updates" + AppConstants.PATH_APPLICATION;
		}
	}; 
	
	public DownloadVersion(DownloadSettings dSettings) throws Exception{
		paths = dSettings.getDownLoadPathsFiles();
		if(paths.getPathToJar() == null || paths.getPathToJar().equals("")){
			PATH_TO_JAR = new File("").getAbsolutePath();   //current directory
		}else{
			PATH_TO_JAR = paths.getPathToJar();
		}
		cfName = dSettings.getServerSettings().toClientConfigurationName();
		DownLoadUtils.configureClientConfigurator(dSettings);
		
		//get the names of the supported versions
		Set<String> vnames = DownLoadUtils.getVersionsNames(dSettings);
		String application = dSettings.getApplication().toLowerCase();
		boolean versionNotFound = true;
		for(String s : vnames){
			if(s.toLowerCase().contains(application)){   //check if name is in the the set of supported applications
				appName = net.is_bg.updatercenter.common.FileUtil.removeFileExtension(s);
				versionNotFound = false;
				break;
			}
		}
		if(versionNotFound)  throw new RuntimeException("Proper " + application + " not fount on server " + dSettings.getServerSettings().toClientConfigurationName());
		//sessionId
		
		sessionid = getSession(requestParams).getSessionId();
	}
	
	private  VersionInfo getVersionInfo() throws  Exception {
		VersionInfo v = Requester.request(cfName).path(MAIN_PATH)
				.subPath(Enumerators.getVersionSubPath(appName)).queryParam(PARAMS.SESSION_ID, sessionid)
				.get(MEDIA_TYPE.JSON).getResponseObject(VersionInfo.class);
		return v;
	}
	
	private Session getSession(RequestParams params) throws  Exception {
		Session session = Requester.request(cfName).path(MAIN_PATH)
				.subPath(Enumerators.getCreateSessionSubPath(appName)).
				 queryParam(PARAMS.MUNICIPALITY_ID, params.municipalityId).
				 queryParam(PARAMS.MUNICIPALITY_NAME, params.municipalityName).
				 queryParam(PARAMS.CURRENT_VERSION, params.currentVersion)
				.get(MEDIA_TYPE.JSON)
				.getResponseObject(Session.class);
		return session;
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
		FileData d =  Requester.request(cfName)
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
		getLatestVersion();
		
		versionDir = paths.getDownloadDir() + File.separator + versionInfo.getNumber();
		
		//save received buffers to a file at the client side...
		downloadChunks();
		
		//Download the library files!!!
		downloadLibs();
		
		//unzip the application without the lib files
		unzip();
		
		copyPackJavaVerZipToJarPathDir();

		//copy libraries to web inf directory
		copyLibsToWebInf();
		
		//zip back to war file
		createWarFile();
		
		//deletes unnecessary files & compares CRC's!!!
		performFinalSteps();
	}
	
	
	/***
	 * Executes command from the command line!!!
	 * @param cmdLineStr - the command String
	 * @param log - the custom logger!!!
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	private void executeCommand(String cmdLineStr, Map<String, String> env) throws IOException, InterruptedException{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmdLineStr);
		Map<String, String > pMap = builder.environment();
		if(env!= null){
			for(Map.Entry<String, String> entry : env.entrySet())
			pMap.put(entry.getKey(), entry.getValue());
		}
        builder.redirectErrorStream(true);
        Process p = builder.start();
       // p.waitFor();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
	}
	
	/**
	 * Determines the latest version
	 * @throws Exception 
	 */
	private void getLatestVersion() throws Exception{
		versionInfo = getVersionInfo();
		System.out.println(versionInfo);
	}
	

	/***
	 * Downloads the chunks of zip without the libraries!!!
	 * @throws Exception
	 */
	private void downloadChunks() throws Exception{
		//create directory with name - the versions number
		String versionDir = paths.getDownloadDir() + File.separator + versionInfo.getNumber();
		FileUtil.createDirIfNotExist(versionDir);
		
		zipVersionFile = versionDir + File.separator +  versionInfo.getFileName() +"nolib";
		res = new File(zipVersionFile);
		OutputStream os = new FileOutputStream(res);
		int i = 0;
		ByteChunk b = new ByteChunk();
		while(i < versionInfo.getChunksNumber()){
		    b.buffer =  getFileByFileName(String.valueOf(i), sessionid);
		    b.size = b.buffer.length;
		    System.out.println("Byte Chunk number " +  i + " with size " + b.size + " Bytes received successfuly" );
			os.write(b.buffer, 0, b.size);
			Thread.sleep(2000);
			i++;
		}
		os.close();
		os.flush();
	}
	
	/**
	 * Downloads the application libraries!!!
	 * @throws Exception
	 */
	private void downloadLibs() throws Exception{
		//create lib dir if not exists
		libdir =  new File(paths.getLibDir());
		if(!libdir.exists()) libdir.mkdir();
		//download the necessary  lib files
		ByteChunk b = new ByteChunk();
		OutputStream os;
		for(String s : versionInfo.libs){
			res = new File(paths.getLibDir() + File.separator +  s);
			if(res.exists()){  continue; }
		    
			b.buffer = getFileByFileName(s, sessionid);
			b.size = b.buffer.length;
		    //save the received buffer to file in lib dir!!!
			os = new FileOutputStream(res);
			os.write(b.buffer, 0, b.size);
			os.close();
			os.flush();
			Thread.sleep(2000);
			System.out.println( "File " + s+"  with size " + b.size + " Bytes received successfuly" );
		}
	}

	/***
	 * This copies the pack_NUMBER_OF_JAVA_VERSION.zip file from lib app lidir to JAR_PATH folder
	 */
	private void copyPackJavaVerZipToJarPathDir(){
		String fName = "pack" + JAVA_VERSION.getClientVersion().getNumber() + ".zip";
		try {
			
			//copy pack_NUMBER_OF_JAVA_VERSION.zip file from lib directory to  the PATH_TO_JAR directory!!!
			File sourcef = new File(paths.getLibDir() +File.separator + fName);
			File destF = new File(PATH_TO_JAR +File.separator + fName);
			System.out.println("copying  " + sourcef.getAbsolutePath() + " to " + destF.getAbsolutePath());
			FileUtil.copyFile(sourcef, destF);
			
			//unzip the zip file into the PATH_TO_JAR directory
			Packager.unZipIt(destF.getAbsolutePath(), PATH_TO_JAR);
			
			//delete source & destination zip files
			sourcef.delete();
			destF.delete();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void unzip(){
		//create unzip app dir if not exists
		unzipDir = versionDir + File.separator + "unzipappdir";
		FileUtil.createDirIfNotExist(unzipDir);
		
		//unzip the application without the libs
		System.out.println("Unzipping " + zipVersionFile + " to " +  unzipDir);
		Packager.unZipIt(zipVersionFile, unzipDir);
	}
	
	private void copyLibsToWebInf() throws IOException{
		//copy the libraries into the application lib dir!!!
		String applicationlibPath = unzipDir + File.separator + "WEB-INF" + File.separator + "lib";
		File applib = new File(applicationlibPath);
		if(!applib.exists()) applib.mkdir();
		System.out.println("Copying lib files  from " + libdir  + " to " + applicationlibPath);
		FileUtil.copyDirectory(new File(paths.getLibDir()), applib);
	}
	
	private void createWarFile() throws IOException, InterruptedException{
		//zip back into the original version war file using jar.exe
		Map<String, String > p  = new  HashMap<String, String>();
		p.put(PATH_TO_UZIPPED_APP, unzipDir);
		p.put(PATH, PATH_TO_JAR);
		p.put(WAR_FILE, versionInfo.getFileName());
		executeCommand(PATH_TO_JAR + File.separator  + BATCH_FILE_NAME, p);
	}
	
	private void performFinalSteps() throws IOException{
		String downloadedVersionFile = unzipDir + File.separator + versionInfo.getFileName();
		
		//delete the .warnolib file
		res = new File(zipVersionFile);
		res.delete();
		
		//copy the war file to the version number directory
		System.out.println("Remove " + versionInfo.getFileName() + " from   " + unzipDir + File.separator + versionInfo.getFileName() + " to " + versionDir + File.separator + versionInfo.getFileName());
		FileUtil.copyFile(new File(unzipDir + File.separator + versionInfo.getFileName()), new File(versionDir + File.separator + versionInfo.getFileName()));
		
		//calculate the crc 32 of  the received file & compare it to received crc 32 hash of the version info
		long receivedFileCrc32 = Crc.checksumMappedFile(downloadedVersionFile);
		System.out.println("Original " + versionInfo.getFileName() + " crc32 is " + versionInfo.crc32);
		System.out.println("Received " + versionInfo.getFileName() + " crc32 is " + receivedFileCrc32);
		
		//delete the unzip directory
		System.out.println("Deleting  directory  " + unzipDir);
		FileUtil.deleteDirectory(new File(unzipDir));
		
	}
	
}
