package version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;
import net.is_bg.controller.AppUtil;
import net.is_bg.controller.ApplicationLibFiles;
import net.is_bg.ltf.Message;
import net.is_bg.ltf.SimpleLock;
import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.TraverseDirsCallBack;
import net.is_bg.updatercenter.common.crc.Crc;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import net.is_bg.updatercenter.common.zippack.Packager;
import file.splitter.MemoryFileSplitter;

/**
 * A class that describes each version, file location, file name format, file extension...
 * @author lubo
 *
 */
public class WarVersionDescriptionEx extends WarVersionDescription {
	
	private final String root;
	private Set<String> libFileSet = new TreeSet<String>();
	
	//dir names 
	private final String unzippedFolder;
	private final String serverLibDir;
	private final String ltfNolibWar;
	
	//absolute paths to dirs & files
	private	final String unzippedFolderPath;
	private	final String warfilePath;
	private	final String applicationlibPath;
	//private	String libCopyDirPath =  root + File.separator +  libCopyDir;
	private	final String ltfNolibWarPath;
	private final String libDescriptionFile;
	private int chunkSize = 0;
	private String warFileName = "";
	private int prefixLength;
	private IModalDailogProvider modalDialogProvider;
	
	/**A map that contains the the library file names + the chunks number as string.
	 * The boolean value indicates whether file is on disk or it's a chunk. 
	 * The lib files will be on disk & the chunks either in memory or on disk depending on the file splitter!!!
	 * Used to refer to both chunks and library files only by name.True value indicates file is on disk and false indicates 
	 * file is chunk!*/
	private Map<String, Boolean> fileNameMap = new Hashtable<String, Boolean>();
	
	
	public WarVersionDescriptionEx(
			String root, String warfile,  int chunkSize, int prefixLength , boolean deploy, IModalDailogProvider modalDialogProvider
			) {
		this.modalDialogProvider = modalDialogProvider;
		String wNameWithouExt = FileUtil.removeFileExtension(warfile);
		this.warFileName = warfile;
		this.prefixLength = prefixLength;
		//create the directory wieht the version
		FileUtil.createDirIfNotExist(root + File.separator + wNameWithouExt);
		this.root = root + File.separator + wNameWithouExt;
		this.chunkSize = chunkSize;
			
		
		//dir names 
		unzippedFolder =  wNameWithouExt + "_unzipped";
		serverLibDir = (String)CONTEXTPARAMS.UPDATE_CENTER_LIB_DIR.getValue();
		ltfNolibWar =  wNameWithouExt + "_nolib";
		
		 //absolute paths to dirs & files
		unzippedFolderPath = this.root + File.separator +  unzippedFolder;
		warfilePath = CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue()  + File.separator +   warfile;
		applicationlibPath = unzippedFolderPath + File.separator + "WEB-INF\\lib";
		//private	String libCopyDirPath =  root + File.separator +  libCopyDir;
	    ltfNolibWarPath = this.root  + File.separator +  ltfNolibWar;
		libDescriptionFile = this.root  + File.separator + warfile + ".libs.txt";
		versionInfo = new  VersionInfo();
		this.fileDestination = warfilePath;
		int lastSlashInd = fileDestination.lastIndexOf("\\");
		String version = lastSlashInd > 0 ? fileDestination.substring(lastSlashInd+1) : fileDestination;
		this.versionInfo.fileName  = version;
		
	}
	
	
	
	
	private String getPattern() {
		// TODO Auto-generated method stub
		File child = new File(root + File.separator +  warFileName);
		for(String pt : VERSION_VALIDATION_PATTERNS.PATTERNS.getPatterns()){
			if(matchVersionPattern(child.getName(), pt)){
				try{
					return pt;
				}catch(Exception ex){
					System.out.println(ex);
					continue;
				}
			}
		}
		return null;
	}
	
	
	
	/***
	 * Matches a version file name against the pattern!!!
	 * @param fileName
	 * @param pattern
	 * @return
	 */
	public boolean  matchVersionPattern(String fileName, String pattern){
		boolean validPattern = false;
		VersionValidator v = null;
		try{
			v = new VersionValidator(fileName, pattern);
			validPattern = v.validate();
		}catch(Exception e){
			validPattern = false;
		}
		
		return validPattern;
	}

	
	/**
	 * Get the lock for this version from version locks!!!
	 * @return
	 */
	private SimpleLock getLock(){
		return WarVersionLocks.getLock(FileUtil.removeFileExtension(this.versionInfo.fileName));
	}
	
	


	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		try {
			SimpleLock lock = getLock();
			if(!lock.lock(new Message(fileDestination + " is being deleted..."))){
				try{
					if(!isDeletable()) throw new RuntimeException("File is not deletetable, status  " + status);
					super.delete();
				}finally{
					lock.unLock();
				}
			}else{
				throw new RuntimeException(lock.getMessage().getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setErrorMsg(e.getMessage());
			//throw new RuntimeException(e);
		}
	}
	
	

	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		try {
			SimpleLock lock = getLock();
			
			if(!lock.lock(new Message(fileDestination + " is being delpoyed..."))){
				try{
					if(!isDeployable()) throw new RuntimeException("File is not deployable, status  " + status);
					deployWar(true);
					super.deploy();
				}finally{
					lock.unLock();
					
				}
			}else{
				throw new RuntimeException(lock.getMessage().getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setErrorMsg(e.getMessage());
			//throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void stoppublish() {
		// TODO Auto-generated method stub
		try {
			
			SimpleLock lock = getLock();
			if(!lock.lock(new Message(fileDestination + " is being published..."))){
				try{
					if(!isStoppublishable()) throw new RuntimeException("File is not stop publishable, status  " + status);
					super.stoppublish();
				}finally{
					lock.unLock();
				}
			}else{
				throw new RuntimeException(lock.getMessage().getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setErrorMsg(e.getMessage());
			//throw new RuntimeException(e);
		}
	}
	
	private void setErrorMsg(String message){
		if(modalDialogProvider != null) modalDialogProvider.getModalDialog().setErrMsg( message);
	}
	
	private void clearModal(){
		if(modalDialogProvider != null) modalDialogProvider.getModalDialog().clear();
	}
	
	@Override
	public void publish() {
		// TODO Auto-generated method stub
		SimpleLock lock = getLock();
		if(!lock.lock()){
			if(!isPublishable()) throw new RuntimeException("File is not publisable, status  " + status);
			//read the library files
			try{
				Set<String> libFileSet = new TreeSet<String>();
				BufferedReader br = new  BufferedReader(new FileReader(new File(libDescriptionFile)));
				List<String> lib = new  ArrayList<>();
				String l = br.readLine();
				while (l!=null) {
					lib.add(l);
					libFileSet.add(l);
					fileNameMap.put(l, true);
					l = br.readLine();
				}
				br.close();
				versionInfo.libs = lib;
				
				//split the war file without the libs into the memory
				this.splitter = new MemoryFileSplitter(new File(ltfNolibWarPath), chunkSize);
				splitter.split();
				versionInfo.chunksNumber = splitter.getChunksNumber();
				versionInfo.number = versionNumber;
				versionInfo.crc32 = Crc.checksumMappedFile(fileDestination);
				
				//now put the chunks into fname map
				long cnumber = 0;
				for(;cnumber < this.splitter.getChunksNumber(); cnumber++){
					fileNameMap.put(String.valueOf(cnumber), false);
				}
				super.publish();
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally{
				lock.unLock();
			}
		}
	}
	
	
	
	
	
	private void deployWar(boolean deploy) throws IOException{
		//unzip the version
		if(deploy){
			fileNamePattern = getPattern();
			if(fileNamePattern == null) throw new RuntimeException("No pattern matches the war file name...");
			String version = versionInfo.fileName;
			this.verValidator = new VersionValidator(version, fileNamePattern);
			verValidator.validate();
			version = version.substring(prefixLength, version.lastIndexOf("."));
			versionNumber = Long.valueOf(version);
			verValidator.setVersionNumber(versionNumber);
			
			System.out.println("Unzipping files  from " + warfilePath + " to " + unzippedFolderPath);
			Packager.unZipIt(warfilePath, unzippedFolderPath);
			
			//copy the libraries copy lib directory
			System.out.println("Copying lib files  from " + applicationlibPath + " to " + serverLibDir);
			addApplicationLibsToServerLibs(applicationlibPath, serverLibDir);
			//FileUtil.copyDirectory(new File(applicationlibPath), new File(serverLibDir));
			
			//delete the lib dir files in the application lib directory!!!
			System.out.println("Deleting lib files  from " +applicationlibPath);
			String libs [] = new File(applicationlibPath).list();  
			for(String s : libs){
				String fName = applicationlibPath +  File.separator + s;
				boolean b = new File(fName).delete();
				System.out.println(fName + ( b ? " deleted " : " not deleted"));
			}
			
			//System.out.println(libs);
			//set the needed libs
			versionInfo.libs = Arrays.asList(libs);
			
			//save the libs to file
			//set the needed libs from copy dir
			//store the lib files into a map and to a description file
			System.out.println("Saving lib file names  into  " + libDescriptionFile);
			PrintWriter libtextFile = new 	PrintWriter(new FileWriter(new File(libDescriptionFile)));
			for(String s: libs){
				libFileSet.add(s);
				libtextFile.println(s);
			}
			libtextFile.close();
			
			//zip back to a file without libraries
			List<File> f = new ArrayList<File>();
			String subFilesAndFolders []  = new File(root + File.separator + unzippedFolder).list();
			for(String s : subFilesAndFolders){
				f.add(new File(unzippedFolderPath + File.separator + s) );
			}
		
			//zip the application files without the libraries
			System.out.println("Zip files back into " + ltfNolibWarPath);
			Packager.packZip(new File(ltfNolibWarPath),  f);
			
		}
	}
	
	/***
	 * Adds the application lib files that are not presented in server lib dir to the server lib dir!!!!
	 * @param applicationlibPath - the application lib folder!!!
	 * @param serverLibPath   - the server lib folder!!!
	 */
	private void addApplicationLibsToServerLibs(String applicationlibPath, String serverLibPath){
		final String serverPath = serverLibPath;
		FileUtil.traverseDirs(new File(applicationlibPath), new TraverseDirsCallBack() {
			@Override
			public void OnReturnFromRecursion(File node) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void OnForward(File node) {
				// TODO Auto-generated method stub
				ApplicationLibFiles f = AppUtil.getApplicationLibFiles();
				String fName = node.getName();
				try{
					if(node.isFile() && !f.contains(fName) ) { 
						FileUtil.copyFile(node.getCanonicalFile(), new File(serverPath + File.separator +fName ));  
						f.addFileToFileSet(fName); 
					}
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public byte[] getFileByFileName(String fileName) {
		// TODO Auto-generated method stub
		byte b [] = null;
		try{
			if(!fileNameMap.containsKey(fileName))   throw new RuntimeException("Requested file doesn't exist...");
			
			if(fileNameMap.get(fileName)){   //the file is on disk
				//read lib file & return bytes
				b = getLibFile(fileName);
			}else{
				//the file is chunk from the file  splitter
				b = splitter.getChunk(Integer.valueOf(fileName));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		return b;
	}
	
	private byte [] getLibFile(String fileName) throws IOException{
		FileInputStream fin = null;
		byte b [] = null;
		try{
			File f = new File(serverLibDir + File.separator+ fileName);
			b  = new byte[(int)f.length()];
			fin = new FileInputStream(f);
			fin.read(b);
			fin.close();
		}finally{
			fin.close();
		}
		return b;
	}
	
	
	

}
