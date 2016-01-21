package version;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.updatercenter.common.FileUtil;


/**
 * Description of all versions updates supported by Server!!!!
 * @author lubo
 *
 */
public class VersionDescriptions {
	
	/***
	 * A map containing the name  version name & description of the version!!!
	 */
	public static Map<String, WarVersionDescription> verNameDescMap = new ConcurrentHashMap<String, WarVersionDescription>();
	
	/**
	 * Creates descriptions for all war files that are located in the VERSIONS_ROOT_DIR & match VERSION_VALIDATION_PATTERNS!!!!
	 */
	public static void initDescriptions(IModalDailogProvider modalDiaDailogProvider, Map<String, Boolean> packZips) {
		String versionDir = (String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue();
		//read version folder & get war files
		File verDir = new  File(versionDir);
		if(!verDir.isDirectory()) return;
		String [] files = verDir.list();
		for(String f : files){  initDescription(f, modalDiaDailogProvider, packZips); }
	}
	
	/***
	 * Init a description by file Name! Deploys a war file and adds it to verNameDescMap!
	 * @param f
	 */
	public static void initDescription(String f, IModalDailogProvider modalDiaDailogProvider, Map<String, Boolean> packZips){
		File child = new File(f);
		if(child.isDirectory() || !f.toLowerCase().endsWith(".war")) return;
		String versionDir = (String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue();
		String verNumberPrefix = (String)CONTEXTPARAMS.UPDATE_CENTER_VERSION_NUMBER_PREFIX.getValue();
		
		String prefix = child.getName().split(verNumberPrefix)[0];
		WarVersionDescriptionEx	d = new WarVersionDescriptionEx(versionDir,
				child.getName(),  (Integer)CONTEXTPARAMS.UPDATE_CENTER_CHUNK_SIZE.getValue(), prefix.length() + verNumberPrefix.length(), true, modalDiaDailogProvider, packZips);
		addWarVersionDescription(FileUtil.removeFileExtension(child.getName()),   d);
	}
	
	/***
	 * Get the getDescriptions of versions !!!
	 * @return
	 */
	public static List<WarVersionDescription> getDescriptions(){
		List<WarVersionDescription> l = new ArrayList<WarVersionDescription>();
		for(WarVersionDescription e: verNameDescMap.values()) {l.add(e);}
		return l;
	}
	
	/***
	 * Retrieves WarVersionDescription by name!!!
	 * @param versionName
	 * @return
	 */
	public static  WarVersionDescription getVersionDescription(String versionName){
		return verNameDescMap.get(versionName);
	}
	
	
	/***
	 * Adds a version description to the description map!!!
	 * @param versionName
	 * @param description
	 */
	public static void addWarVersionDescription(String versionName, WarVersionDescription description){
		verNameDescMap.put(versionName, description);
		WarVersionLocks.addLockifNotExist(versionName);
	}

	public static void removeVersionDescription(String fileName) {
		// TODO Auto-generated method stub
		verNameDescMap.remove(fileName);
		WarVersionLocks.removeLock(fileName);
	}
	
	
	
}
