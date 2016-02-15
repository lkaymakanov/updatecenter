package net.is_bg.updatercenter.common.zippack;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


// TODO: Auto-generated Javadoc
/**
 * The Class Packager.
 */
public class Packager
{
    
	
	public static void main(String []args) throws IOException{

		/*File f =new File("D:\\downloadversion\\versions\\unzip_real");
		File out = new File("D:\\downloadversion\\versions\\LTF-xxxx.war");
		List<File> ff = new  ArrayList<File>();
		for(File s : f.listFiles()){
			ff.add( s );
		}
		Packager.packZip(out, ff);
	
		//Packager.unZipIt("D:\\downloadversion\\versions\\LTF-xxxx.war", "D:\\downloadversion\\versions\\unzip");
		String orgEntries = listEntries("D:\\downloadversion\\versions\\LTF_real.war");
		System.out.println(orgEntries);
		PrintWriter wr = new  PrintWriter(new File("out_real.txt"));
		wr.println(orgEntries);
		wr.close();
		
		orgEntries = listEntries("D:\\downloadversion\\versions\\LTF-xxxx.war");
		System.out.println(orgEntries);
		wr = new  PrintWriter(new File("out_xxxx.txt"));
		wr.println(orgEntries);
		wr.close();
		//String orgEntry = listEntries("D:\\downloadversion\\versions\\LTF-1234.war");*/
		
		
    }
	
    /**
     * Pack zip.
     *
     * @param output the output
     * @param sources the sources
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void packZip(File output, List<File> sources) throws IOException
    {
        System.out.println("Packaging to " + output.getName());
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(output));
       // zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);

        for (File source : sources)
        {
            if (source.isDirectory())
            {
                zipDir(zipOut, "", source);
            } else
            {
                zipFile(zipOut, "", source);
            }
        }
        zipOut.flush();
        zipOut.close();
        System.out.println("Done");
    }

    /**
     * Builds the path.
     *
     * @param path the path
     * @param file the file
     * @return the string
     */
    private static String buildPath(String path, String file)
    {
        if (path == null || path.isEmpty())
        {
            return file;
        } else
        {
            return path +"/" + file;
        }
    }

    /**
     * Zip dir.
     *
     * @param zos the zos
     * @param path the path
     * @param dir the dir
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void zipDir(ZipOutputStream zos, String path, File dir) throws IOException
    {
        if (!dir.canRead())
        {
            System.out.println("Cannot read " + dir.getCanonicalPath() + " (maybe because of permissions)");
            return;
        }

        File[] files = dir.listFiles();
        
        path = buildPath(path, dir.getName());
        String entryName = path;
        zos.putNextEntry(new ZipEntry(entryName + "/"));
        //System.out.println("Adding Directory " + path);

        for (File source : files)
        {
            if (source.isDirectory())
            {
                zipDir(zos, path, source);
            } else
            {
                zipFile(zos, path, source);
            }
        }

        //System.out.println("Leaving Directory " + path);
    }

    /**
     * Zip file.
     *
     * @param zos the zos
     * @param path the path
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void zipFile(ZipOutputStream zos, String path, File file) throws IOException
    {
        if (!file.canRead())
        {
            System.out.println("Cannot read " + file.getCanonicalPath() + " (maybe because of permissions)");
            return;
        }

        String entryName = buildPath(path, file.getName());
        System.out.println(entryName);
       // System.out.println("Compressing " + file.getName());
        zos.putNextEntry(new ZipEntry(entryName));

        FileInputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[409200];
        int byteCount = 0;
        while ((byteCount = fis.read(buffer)) != -1)
        {
            zos.write(buffer, 0, byteCount);
            //System.out.print('.');
            System.out.flush();
        }
       // System.out.println();

        fis.close();
        zos.closeEntry();
    }
    
    
    public static  String listEntries(String zipFile) throws IOException{
    	String s = "";
    	//get the zip file content
    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
    		s+=ze.getName() + "\n";
    		ze = zis.getNextEntry();
    	}
    	return s;
    }
    
    /**
     * Unzip it - MkYoung utils
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void unZipIt(String zipFile, String outputFolder){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
           	
 			 
          // System.out.println("file unzip : "+ newFile.getAbsoluteFile() + (ze.isDirectory() ? " directory " : " file"));
           
 		   if(ze.isDirectory()) {
        	  boolean b =  newFile.mkdir();
        	 // System.out.println(b);
        	   ze = zis.getNextEntry();
        	   continue;
           }else{
        	   System.out.println(fileName);
    		   String ar[] = fileName.split("/");
    			 
			   //create the dir path of the entry
			   String  dirpath = "";
			   for(int i= 0; i < ar.length-1; i++){  //skip the file
				   dirpath+=ar[i];
				   if(i < ar.length -2) dirpath+= File.separator;
			   }
			   
			   if(!dirpath.equals(""))  new SubDirCreator(outputFolder, false).createDirInRootDir(dirpath);
			   
           }
           
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
 
    	System.out.println("Done");
 
    }catch(IOException ex){
       ex.printStackTrace(); 
    }
   }    
    
    /***
     * Creates a subdrir in the root directory passed to the constructor! If brelativePath is true creates directory
     * relative to the current executable file directory!!!
     * @author lubo
     *
     */
    public static class SubDirCreator {

    	
    	private String rootDirhlp;
    	String filePath = "";
    	private boolean bRelarivePath;
    	
    	public SubDirCreator(String rootDirPath, boolean brelativePath){
    		rootDirhlp = rootDirPath;
    		this.bRelarivePath = brelativePath;
    	}

    	//create the directory for model
    	public final void createDirInRootDir(String dirName) throws IOException{
    		if(dirName == null || dirName.equals("")) return;
    		
    		//do some log here
    		/*System.out.println("Entered in create Root Dir ....");
    		System.out.println("Dir Name = " + dirName);*/
    		
    		int ind = dirName.indexOf(File.separator);
    		if(ind == -1){  //path found 
    			createSubDir(rootDirhlp, dirName);  //create last directory
    			rootDirhlp += File.separator + dirName;      //extend path
    			return;
    		}		
    		createSubDir(rootDirhlp, dirName.substring(0, ind));
    		rootDirhlp += File.separator + dirName.substring(0, ind);     //extend path
    		
    		createDirInRootDir(dirName.substring(ind+1));       //call recursively
    	}
    	
    	//create subDir in base path
    	private void createSubDir(String basedir, String subdir) throws IOException{
    		
    		//do some log here
    		/*System.out.println("Entered in createSubDir ....");
    		System.out.println("basedir Name = " + basedir);
    		System.out.println("subdir Name = " + subdir);
    		*/
    		File directory = new File (".");
    		
    		if(bRelarivePath == true){
    			//relative path to executable folder
    			filePath = directory.getCanonicalPath() + basedir + File.separator + subdir;
    		}else{
    			 //absolute path
    			filePath = basedir + File.separator + subdir;
    		}
    		File f =	new  File(filePath);
    		if(!f.exists())f.mkdir();
    	}
    }
    
    
}