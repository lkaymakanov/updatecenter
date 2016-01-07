package version;

import java.io.File;

import net.is_bg.updatercenter.common.FileUtil;
import file.splitter.IFileSplitter;

public abstract class WarVersionDescription extends CommonVersionDescription{

	protected IFileSplitter splitter;
	protected long versionNumber;
	protected VersionValidator verValidator;
	
	
	public long getVersionNumber() {
		return versionNumber;
	}
	
	public abstract byte [] getFileByFileName(String fname);
	
	
	/***
	 * Makes the war file available for update!!!
	 */
	public void publish(){
		status = WAR_FILE_STATUS.PUBLISHED;
	}
	
	/***
	 * Makes the war file not available for update!!!
	 */
	public void stoppublish(){
		status = WAR_FILE_STATUS.NOT_PUBLISHED;
	}
	
	/**
	 * Unzip the war file & extract libs!!!
	 */
	public void deploy(){
		status = WAR_FILE_STATUS.DEPLOYED;
	}
	
	/**
	 * Deletes the war file!!!!
	 */
	public void delete(){
		boolean b = new File(fileDestination).delete();
		if(b){
			VersionDescriptions.removeVersionDescription(FileUtil.removeFileExtension(versionInfo.getFileName()));
		}
	}
	
	public boolean isPublishable(){
		return status == WAR_FILE_STATUS.NOT_PUBLISHED || status == WAR_FILE_STATUS.DEPLOYED;
	}
	
	public boolean isDeletable(){
		return status == WAR_FILE_STATUS.NOT_PUBLISHED ||  status == WAR_FILE_STATUS.NEW_WAR;
	}
	public boolean isStoppublishable(){
		return status == WAR_FILE_STATUS.PUBLISHED;
	}
	public boolean isDeployable(){
		return status == WAR_FILE_STATUS.NEW_WAR ||
				( status != WAR_FILE_STATUS.DEPLOYED  &&  status != WAR_FILE_STATUS.DEPLOYING  && status != WAR_FILE_STATUS.PUBLISHED);
	}
	
}
