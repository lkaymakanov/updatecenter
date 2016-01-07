package version;

import net.is_bg.updatercenter.common.resources.VersionInfo;

public class CommonVersionDescription {

	protected String fileDestination;
	protected String fileNamePattern; 
	protected VersionInfo versionInfo;
	protected WAR_FILE_STATUS status = WAR_FILE_STATUS.NEW_WAR;
	
	public String getFileDestination() {
		return fileDestination;
	}
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}
	public void setStatus(WAR_FILE_STATUS status){
		this.status = status;
	}

	public WAR_FILE_STATUS getStatus() {
		return status;
	}
	
}
