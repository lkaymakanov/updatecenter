package version;

import net.is_bg.updatercenter.common.resources.VersionInfo;

public class CommonVersionDescription {

	protected String fileDestination;
	protected String fileNamePattern; 
	protected VersionInfo versionInfo;
	
	
	public String getFileDestination() {
		return fileDestination;
	}
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}
	
}
