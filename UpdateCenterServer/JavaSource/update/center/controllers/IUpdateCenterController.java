package update.center.controllers;

import java.util.List;
import java.util.Set;

import net.is_bg.updatercenter.common.FileData;
import net.is_bg.updatercenter.common.Ping;
import net.is_bg.updatercenter.common.RequestParams;
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
	public FileData getFileByName(RequestParams params);
	
	/***
	 * Gets the names of the available applications for update + Extra information about the files!!!
	 * @return
	 */
	public String getAvailableUpdates();
	
	/**
	 * Returns the id's of the active sessions!!!!
	 * @param info
	 * @return
	 */
	public Set<String> getSessionsIds();
	/**
	 * Returns the set with the lib files in lib directory!!!!
	 * @param info
	 * @return
	 */
	public Set<String> getLibraries();
	
	
	/***
	 * Gets the names of the available applications for update!!!
	 * @return
	 */
	public Set<String>  getVersionNames();
	
	
	/***
	 * Get Session complete session info
	 * @return
	 */
	public List<Session> getSessionsInfo();

	
	/**
	 * Returns basic info about server if available!!!
	 * @return
	 */
	public Ping ping();
	
}
