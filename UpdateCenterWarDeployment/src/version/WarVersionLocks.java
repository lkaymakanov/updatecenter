package version;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.is_bg.ltf.SimpleLock;

/**
 * A class that contains locks for war files that are being managed !!!
 * @author lubo
 *
 */
public class WarVersionLocks {

	/***
	 * The key of the map is the war file name!!!
	 */
	private static Map<String, SimpleLock> locks  = new ConcurrentHashMap<String, SimpleLock>();
	
	/**
	 * Creates a new entry lock for war file if not exists otherwise returns immediately
	 * @param warfileName
	 */
	public static void addLockifNotExist(String warfileName){
		if(!locks.containsKey(warfileName)) locks.put(warfileName, new SimpleLock()); 
	}
	
	/**
	 * Returns the lock for war file!!!
	 * @param warfileName
	 * @return
	 */
	public static SimpleLock getLock(String warfileName){
		return locks.get(warfileName);
	}

	public static void removeLock(String removeFileExtension) {
		// TODO Auto-generated method stub
		locks.remove(removeFileExtension);
	}
}
