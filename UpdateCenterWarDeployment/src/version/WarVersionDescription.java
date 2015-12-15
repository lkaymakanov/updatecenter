package version;

import file.splitter.IFileSplitter;

public abstract class WarVersionDescription extends CommonVersionDescription{

	protected IFileSplitter splitter;
	protected long versionNumber;
	protected VersionValidator verValidator;
	
	public long getVersionNumber() {
		return versionNumber;
	}
	
	public abstract byte [] getFileByFileName(String fname);
	
}
