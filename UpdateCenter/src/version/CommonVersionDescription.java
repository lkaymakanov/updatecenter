package version;

import net.is_bg.updatercenter.common.resources.VersionInfo;
import file.splitter.IFileSplitter;

public class CommonVersionDescription {

	String fileDestination;
	String fileNamePattern; 
	IFileSplitter splitter;
	long versionNumber;
	VersionValidator verValidator;	
	VersionInfo versionInfo;
	
	
	public String getFileDestination() {
		return fileDestination;
	}
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	public IFileSplitter getSplitter() {
		return splitter;
	}
	public long getVersionNumber() {
		return versionNumber;
	}
	public VersionValidator getVerValidator() {
		return verValidator;
	}
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}
	
	public byte[] getChunk(long number){
		return splitter.getChunk((int)number);
	}
	
	public long getChunkSize(long number){
		return splitter.getChunkSize((int)number);
	}
	
	public byte [] getFileByFileName(String fileName){
		return null;
	}
	
}
