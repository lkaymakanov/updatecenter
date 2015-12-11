package net.is_bg.controller;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import net.is_bg.updatercenter.common.AppConstants;
import net.is_bg.updatercenter.common.resources.Session;
import net.is_bg.updatercenter.common.resources.VersionInfo;
import version.CommonVersionDescription;
import version.VersionDescriptions;

@Path(AppConstants.PATH_APPLICATION)
public class UpdateCenterController {
	
	
	/***
	 * Returns information about the application version!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appname}/versioninfo/")
	public VersionInfo getUpdate(@Context UriInfo info){
		System.out.println(info.getAbsolutePath());
		List<String> s = info.getQueryParameters().get("sessionid");
		String [] a  =  info.getPath(true).split("/");
		String sessionId = (s ==null || s.size() < 1 ? null  : s.get(0));
		System.out.println(info.getPath(true));
		return  UpdateCenterDispatcher.dispatchVersionInfoRequest(a[1], sessionId);//VersionDescriptions.getLtfDescription().getVersionInfo();
	}
	
	
	/***
	 * Creates a new session between the client & the server!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appname}/session/")
	public Session getSession(@Context UriInfo info){
		System.out.println(info.getAbsolutePath());
		System.out.println(info.getQueryParameters().get("sessionid"));
		System.out.println(info.getPath(true));
		return AppUtil.getSessionRegister().getSession("-1", true);
	}
	

	/***
	 * Returns a file by fileName!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appname}/file/{name}/")
	public byte [] getFileByName(@Context UriInfo info){
		System.out.println(info.getAbsolutePath());
		List<String> s = info.getQueryParameters().get("sessionid");
		String [] a  =  info.getPath(true).split("/");
		String sessionId = (s ==null || s.size() < 1 ? null  : s.get(0));
		return   UpdateCenterDispatcher.dispatchFileRequest(a[1], a[a.length-1], sessionId);//VersionDescriptions.getLtfDescription().getFileByFileName(a[a.length-1]);
	}
	
	/***
	 * Gets the names of the available applications for update!!!
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updates/")
	public String getAvailableUpdates(){
		String s = "";
		for(CommonVersionDescription v : VersionDescriptions.getDescriptions()){
			s+=v.getVersionInfo() + "\n";
		}
		return s;
	}
	
	/**
	 * Returns the id's of the active sessions!!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sessions/")
	public Set<String> getSessions(@Context UriInfo info){
		return AppUtil.getSessionRegister().getSessionIds();
	}
	
	
	/**
	 * Returns the set with the lib files in lib directory!!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/libs/")
	public Set<String> getLibraries(){
		return AppUtil.getApplicationLibFiles().getLibFiles();
	}
	
	
	
}
