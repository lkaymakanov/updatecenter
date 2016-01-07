package net.is_bg.controller;

import version.VersionDescriptions;
import net.is_bg.updatercenter.common.FileData;
import net.is_bg.updatercenter.common.resources.VersionInfo;


/**
 * Dispatches request to the corresponding versions!!!
 * @author lubo
 *
 */
public class UpdateCenterDispatcher {

	/***
	 * Dispatch file request!!!
	 * @param application
	 * @param filename
	 * @param sessionId
	 * @return
	 */
	public static FileData dispatchFileRequest(String application, String filename, String sessionId){
		System.out.println("Entered in dispatchFileRequest: app is " + application + " fileName is  " + filename + " sessionid is " + sessionId);
		checkSession(sessionId);
	    return new FileData(VersionDescriptions.getVersionDescription(application).getFileByFileName(filename));
	}
	
	
	/***
	 * Dispatch version info request !!!
	 * @param application
	 * @param sessionId
	 * @return
	 */
	public static VersionInfo dispatchVersionInfoRequest(String application,  String sessionId){
		System.out.println("Entered in dispatchVersionInfoRequest: app is " + application +  " sessionid is " + sessionId);
		checkSession(sessionId);
		return VersionDescriptions.getVersionDescription(application).getVersionInfo();
	}
	
	/**
	 * If session is active  
	 * @param sessionId
	 */
	private static void checkSession(String sessionId){
		if(!AppUtil.getSessionRegister().isSessionActive(sessionId)){throw new RuntimeException(sessionId + " with sessionId is not active..."); }else{ AppUtil.getSessionRegister().getSession(sessionId, false, null); }
	}
}
