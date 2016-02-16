package net.is_bg.updatercenter.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public final class FileUtil {
	
	public static String removeFileExtension(String fName){
		int i = fName.lastIndexOf(".");
		if(i > -1) {
			return fName.substring(0, i);
		}
		return fName;
	}
	
	/***
	 * Creates a directory if not exists!!!
	 * @param dirName
	 */
	public static void createDirIfNotExist(String dirName){
		File f = new File(dirName);
		List<String> dirs = new  ArrayList<String>();
		dirs.add(f.getAbsolutePath());
		File parent = f.getParentFile();
		while(parent!=null){
			dirs.add(parent.getAbsolutePath());
			parent = parent.getParentFile();
		}
		
		//create the parent dirs if not exists!!!
		for(int i= dirs.size()-1; i >=0 ;i--){
			//System.out.println(dirs.get(i));
			File ff = new File(dirs.get(i)); 
			if(!ff.exists()) ff.mkdir();
		}
	}
	
	/***
	 * Deletes a directory !!!
	 */
	public static void deleteDirectory(File directory){
		//delete unzip directory
		FileUtil.traverseDirs(directory, new TraverseDirsCallBack() {
			@Override
			public void OnReturnFromRecursion(File node) {
				// TODO Auto-generated method stub
				node.delete();
			}
			@Override
			public void OnForward(File node) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/***
	 * Load properties from property file
	 * 
	 */
	public static Properties loadProperties(String propertyFile){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(propertyFile);

			// load a properties file
			prop.load(input);
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	
	
	
	
	public static void saveProperties(Properties p, String propertyFile){
		try {
			OutputStream os = new  FileOutputStream(propertyFile);
			p.store(os, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**Traverse directory trees
	 * 
	 * @param node - the current file node
	 * @param trcallback  - what is to be done forward & backward
	 */
	public static void traverseDirs(File node, TraverseDirsCallBack trcallback){
		
		if(trcallback != null)trcallback.OnForward(node);
		//System.out.println(node.getAbsoluteFile());
		
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				traverseDirs(new File(node, filename), trcallback);
				if(trcallback != null) trcallback.OnReturnFromRecursion(node);//act on the directory
			}
		}
		if(trcallback != null) trcallback.OnReturnFromRecursion(node);  //act on the file
	}
	
	/**
	 * Copies the sourceLocation directory together with all its contents to the targetLocation directory!!! If targetLocation doesn't exists it is being created!!!
	 * @param sourceLocation - the source folder location 
	 * @param targetLocation - the destination folder location
	 * @throws IOException
	 */
	public static  void copyDirectory(File sourceLocation , File targetLocation)throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            try{
	            // Copy the bits from instream to outstream
	            byte[] buf = new byte[1024*1024];
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
            }finally{
            	in.close();
            	out.close();
            }
        }
	}
	
	/**
	 * Copies a file from one destination to another!!! 
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!sourceFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}
	}
	
}
