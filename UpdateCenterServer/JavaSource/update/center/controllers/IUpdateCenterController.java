package update.center.controllers;

import java.util.Set;

import net.is_bg.updatercenter.common.resources.Session;
import net.is_bg.updatercenter.common.resources.VersionInfo;

public interface IUpdateCenterController {

	/***
	 * Returns information about the application version!!!
	 * @param info
	 * @return
	 */
	
	public VersionInfo getVersionInfo(RequestParams params);
	
	/***
	 * Creates a new session between the client & the server!!!
	 * @param info
	 * @return
	 */
	public Session getSession(RequestParams params);

	/***
	 * Returns a file by fileName!!!
	 * @param info
	 * @return
	 */
	public byte [] getFileByName(RequestParams params);
	
	/***
	 * Gets the names of the available applications for update!!!
	 * @return
	 */
	public String getAvailableUpdates();
	
	/**
	 * Returns the id's of the active sessions!!!!
	 * @param info
	 * @return
	 */
	public Set<String> getSessions();
	/**
	 * Returns the set with the lib files in lib directory!!!!
	 * @param info
	 * @return
	 */
	public Set<String> getLibraries();
	
	
}
