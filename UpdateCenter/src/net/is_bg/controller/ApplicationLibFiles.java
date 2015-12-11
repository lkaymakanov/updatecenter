package net.is_bg.controller;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;

/***
 * Represents the directory on the update server containing the necessary libraries for all the applications that require update!!!
 * @author lubo
 *
 */
public class ApplicationLibFiles {
	
	private static ApplicationLibFiles f;
	private ApplicationLibFiles(){
		
	}

	/***
	 * The set with the lib files!!!!
	 */
	private Set<String>  libFiles = new  ConcurrentSkipListSet<String>();
	
	public   void addFileToFileSet(String fileName){
			System.out.println(fileName + " added to lib file set....");
			libFiles.add(fileName);
	}
	
	/**
	 * Check if the lib directory contains the file with fileName!!!
	 * @param fileName
	 * @return
	 */
	public boolean contains(String fileName){
			return libFiles.contains(fileName);
	}
	
	public  static ApplicationLibFiles  getApplicationLibFiles() {
		if(f == null) f = new  ApplicationLibFiles(); 
		return f;
	}
	
	
	public  static void  initApplicationLibFiles(String libDir) {
		if(f == null) f = new  ApplicationLibFiles();
		FileUtil.traverseDirs(new File((String)CONTEXTPARAMS.SERVER_LIB_DIR.getValue()), new TraverseDirsCallBack() {
			@Override
			public void OnReturnFromRecursion(File node) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void OnForward(File node) {
				if(node.isFile()) {ApplicationLibFiles.getApplicationLibFiles().addFileToFileSet(node.getName());}
			}
		});
		
	}
	
	
	
	public Set<String>  getLibFiles(){
		return libFiles;
	}
}
