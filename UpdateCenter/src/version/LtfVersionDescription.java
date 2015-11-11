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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.is_bg.controller.FileUtil;
import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.updatercenter.common.crc.Crc;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import net.is_bg.updatercenter.common.zippack.Packager;
import file.splitter.MemoryFileSplitter;

/**
 * A class that describes each version, file location, file name format, file extension...
 * @author lubo
 *
 */
public class LtfVersionDescription extends CommonVersionDescription {
	
	private String root = (String)CONTEXTPARAMS.LTF_UPDATE_ROOT_DIR.getValue();
	private Set<String> libFileSet = new TreeSet<String>();
	
	//dir names 
	private String unzippedFolder = "ltfunzipped";
	private String warfile =   (String)CONTEXTPARAMS.LTF_WAR_FILE.getValue();
	private String libCopyDir = "libs";
	private String ltfNolibWar = warfile + "nolib";
	
	//absolute paths to dirs & files
	private	String unzippedFolderPath = root + File.separator +  unzippedFolder;
	private	String warfilePath = root + File.separator +   warfile;
	private	String applicationlibPath = unzippedFolderPath + File.separator + "WEB-INF\\lib";
	private	String libCopyDirPath =  root + File.separator +  libCopyDir;
	private	String ltfNolibWarPath = root + File.separator +  ltfNolibWar;
	private String libDescriptionFile = root + File.separator + warfile + ".libs";
	
	//private int verNumbers = 4;
	private String pattern = "[lL][tT][fF]-1.2-(\\d)+.[wW][aA][rR]";
	private int chunkSize = 5*1024*1024;
	private VersionInfo info = new VersionInfo();
	
	
	public LtfVersionDescription() throws IOException{
		this.fileDestination = warfilePath;
		int lastSlashInd = fileDestination.lastIndexOf("\\");
		String version = lastSlashInd > 0 ? fileDestination.substring(lastSlashInd+1) : fileDestination;
		this.verValidator = new VersionValidator(version, pattern);
		verValidator.validate();
		info.fileName = version;
		version = version.substring(8, version.lastIndexOf("."));
		versionNumber = Long.valueOf(version);
		verValidator.setVersionNumber(versionNumber);
		
		//get out of the libraries in war file
		if((boolean)CONTEXTPARAMS.LTF_DEPLOY.getValue()) deployWar();
		
		//read the libs file
		BufferedReader br = new  BufferedReader(new FileReader(new File(libDescriptionFile)));
		List<String> lib = new  ArrayList<>();
		String l = br.readLine();
		while (l!=null) {
			lib.add(l);
			libFileSet.add(l);
			l = br.readLine();
		}
		br.close();
		info.libs = lib;
		
		//split the war file without the libs into the memory
		this.splitter = new MemoryFileSplitter(new File(ltfNolibWarPath), chunkSize);
		splitter.split();
		info.chunksNumber = splitter.getChunksNumber();
		info.number = versionNumber;
		info.crc32 = Crc.checksumZip(fileDestination);
	}
	

	
	
	private void deployWar() throws IOException{
		//unzip the version
		System.out.println("Unzipping files  from " + warfilePath + " to " + unzippedFolderPath);
		Packager.unZipIt(warfilePath, unzippedFolderPath);
		
		
		//copy the libraries copy lib directory
		System.out.println("Copying lib files  from " + applicationlibPath + " to " + libCopyDirPath);
		FileUtil.copyDirectory(new File(applicationlibPath), new File(libCopyDirPath));
		
		//delete the lib dir files in the application lib directory!!!
		System.out.println("Deleting lib files  from " +applicationlibPath);
		String libs [] = new File(applicationlibPath).list();  
		for(String s : libs){
			String fName = applicationlibPath +  File.separator + s;
			boolean b = new File(fName).delete();
			//System.out.println(fName + ( b ? " deleted " : " not deleted"));
		}
		
		//System.out.println(libs);
		//set the needed libs
		info.libs = Arrays.asList(libs);
		
		//save the libs to file
		//set the needed libs from copy dir
		//store the lib files into a map and to a decription file
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
	
	@Override
	public byte[] getFileByFileName(String fileName) {
		// TODO Auto-generated method stub
		byte b [] = null;
		FileInputStream fin = null;
		try{
			if(libFileSet.contains(fileName)){
				//read lib file & 
				File f = new File(libCopyDirPath + File.separator+ fileName);
				b  = new byte[(int)f.length()];
				fin = new FileInputStream(f);
				fin.read(b);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}finally{
			try {
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return b;
	}
	
	
	public long getVersionNumber(){
		return verValidator.getVersionNumber();
	}
	
	
	public byte[] getChunkNumber(int number){
		return splitter.getChunk(number);
	}
	
	public long getChunkSize(int number){
		return splitter.getChunkSize(number);
	}
	
	public VersionInfo getVersionInfo(){
		return info;
	}

}
