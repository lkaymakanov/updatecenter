package net.is_bg.controller;

import version.VersionDescriptions;
import net.is_bg.updatercenter.common.resources.VersionInfo;


/**
 * Dispatches request to the corresponding versions!!!
 * @author lubo
 *
 */
public class UpdateCenterDispatcher {

	public static byte []  dispatchFileRequest(String application, String filename, String sessionId){
		System.out.println("Entered in dispatchFileRequest: app is " + application + " fileName is  " + filename + " sessionid is " + sessionId);
	    return VersionDescriptions.getVersionDescription(application).getFileByFileName(filename);
	}
	
	
	public static VersionInfo dispatchVersionInfoRequest(String application,  String sessionId){
		System.out.println("Entered in dispatchVersionInfoRequest: app is " + application +  " sessionid is " + sessionId);
		return VersionDescriptions.getVersionDescription(application).getVersionInfo();
	}
}
