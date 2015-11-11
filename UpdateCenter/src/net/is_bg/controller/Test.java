package net.is_bg.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.updatercenter.common.zippack.Packager;



public class Test {
	
	public static void main(String [] args) throws IOException{
		
		String root = "D:\\updatecenterroot";
		
		//dir names 
		String unzippedFolder = "ltfunzipped";
		String warfile =   "LTF-1.3-6866.war";
		String libCopyDir = "libcopy";
		String ltfNolibWar = "LTF-1.3-6866.warnolib";
		
		//absolute paths to dirs & files
		String unzippedFolderPath = root + File.separator +  unzippedFolder;
		String warfilePath = root + File.separator +   warfile;
		String applicationlibPath = unzippedFolderPath + File.separator + "WEB-INF\\lib";
		String libCopyDirPath =  root + File.separator +  libCopyDir;
		String ltfNolibWarPath = root + File.separator +  ltfNolibWar;
		
		//unzip the version
		Packager.unZipIt(warfilePath, unzippedFolderPath);
		
		//copy the libraries copy lib directory
		FileUtil.copyDirectory(new File(applicationlibPath), new File(libCopyDirPath));
		
		//delete the lib dir files
		String libs [] = new File(applicationlibPath).list();  
		for(String s : libs){
			String fName = applicationlibPath +  File.separator + s;
			boolean b = new File(fName).delete();
			//System.out.println(fName + ( b ? " deleted " : " not deleted"));
		}
		//System.out.println(libs);
		
		//zip back to a file without libraries
		List<File> f = new ArrayList<File>();
		String subFilesAndFolders []  = new File( root + File.separator + unzippedFolder).list();
		for(String s : subFilesAndFolders){
			f.add(new File(unzippedFolderPath + File.separator + s) );
		}
		
		//zip the application files without the libraries
		Packager.packZip(new File(ltfNolibWarPath),  f);
		
	}

	
}
