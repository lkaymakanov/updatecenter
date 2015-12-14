package version;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.is_bg.controller.AppConstants.CONTEXTPARAMS;
import net.is_bg.controller.AppConstants.VERSION_VALIDATION_PATTERNS;


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
	public static void initDescriptions() {
		String versionDir = (String)CONTEXTPARAMS.UPDATE_CENTER_VERSIONS_DIR.getValue();
		//read version folder & get war files
		File verDir = new  File(versionDir);
		if(!verDir.isDirectory()) return;
		
		for(String f : verDir.list()){
			File child = new File(f);
			if(child.isDirectory()) continue;
			
			for(VERSION_VALIDATION_PATTERNS pt : VERSION_VALIDATION_PATTERNS.values()){
				if(matchVersionPattern(child.getName(), pt.getPattern())){
					String prefix = child.getName().split("-1.2-")[0];
					try{
						addWarVersionDescription(child.getName(),   new WarVersionDescriptionEx(versionDir, 
								child.getName(), pt.getPattern(), 5*1024*1024, prefix.length() + 5, true) );
					}catch(Exception ex){
						continue;
					}
				}
				
			}
		}
	}
	
	public static Collection<WarVersionDescription> getDescriptions(){
		return verNameDescMap.values();
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
	}
	
	/***
	 * Matches a version file name against the pattern!!!
	 * @param fileName
	 * @param pattern
	 * @return
	 */
	private static boolean  matchVersionPattern(String fileName, String pattern){
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
	
}
