package version;

import java.io.File;
import java.util.Set;

import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.TraverseDirsCallBack;
import net.is_bg.updatercenter.common.zippack.Packager;

public class WarHelper {

	/**
	 * Extracts the libraries from a war file & saves them to the destination lib folder!!!!
	 */
	
	public static void extractLibsFromWar(String pathtoWar, String pathTolibDestination, Set<String> libdestinationFileSet){
		FileUtil.createDirIfNotExist(pathTolibDestination);
		String unzipFolder = "";
		File war = new File(pathtoWar);
		unzipFolder +=(war.getParent() +  File.separator + war.getName() +  "_unzipfolder");
		
		System.out.println("Unzipping files  from " + pathtoWar + " to " + pathTolibDestination);
		Packager.unZipIt(pathtoWar, unzipFolder);
		
		copyLibs(unzipFolder + File.separator + net.is_bg.controller.AppConstants.WEB_INF_LIBS, pathTolibDestination,libdestinationFileSet);
		
		//delete unzip directory
		FileUtil.deleteDirectory(new File(unzipFolder));
	}
	
	
	
	/**
	 * Copies libs from the source folder to the destination folder if not present in the libdestinationFileSet!!!
	 * @param pathtoWar
	 * @param pathTolibDestination
	 * @param libdestinationFileSet
	 */
	private static void copyLibs(String source, String destination, Set<String> libdestinationFileSet){
		
		final String serverPath = destination;
		final Set<String> libSet  = libdestinationFileSet;
		
		FileUtil.traverseDirs(new File(source), new TraverseDirsCallBack() {
			@Override
			public void OnReturnFromRecursion(File node) {
				// TODO Auto-generated method stub
			}
			@Override
			public void OnForward(File node) {
				// TODO Auto-generated method stub
				//ApplicationLibFiles f = AppUtil.getApplicationLibFiles();
				String fName = node.getName();
				try{
					if(node.isFile() && !libSet.contains(fName) ) { 
						FileUtil.copyFile(node.getCanonicalFile(), new File(serverPath + File.separator +fName ));  
						libSet.add(fName); 
					}
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public static void main(String [] args){
		//extractLibsFromWar("D:\\updatecenterroot\\versions\\LTF-1.2-6866.war", "D:\\extractedlibs", new TreeSet<String>());
		//FileUtil.deleteDirectory(new File("D:\\somedir"));
		//FileUtil.createDirIfNotExist("D:\\somedir");
	}
}
