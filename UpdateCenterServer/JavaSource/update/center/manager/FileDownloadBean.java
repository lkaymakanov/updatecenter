package update.center.manager;

import net.is_bg.ltf.SimpleLock;
import net.is_bg.updatercenter.common.FileUtil;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import update.center.controllers.AppUtil;
import version.WarVersionLocks;

public class FileDownloadBean {

	/**
	 * Get the lock for  version from version locks!!!
	 * @return
	 */
	private SimpleLock getLock(VersionInfo versionInfo){
		return WarVersionLocks.getLock(FileUtil.removeFileExtension(versionInfo.getFileName()));
	}
	
	
	/*public void download(WarVersionDescription versionInfo){
		System.out.println("entered in download version " + );
		
	}*/
	
	public void download(String fileDestination, String fileName){
		System.out.println("entered in download version " + fileDestination);
		AppUtil.downloadFile(fileDestination, fileName);
	}
	
	
}
