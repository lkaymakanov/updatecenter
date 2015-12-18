package update.center.controllers;

import java.util.List;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import net.is_bg.controller.AppUtil;
import net.is_bg.controller.UpdateCenterDispatcher;
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
	@Path("/{appname}" + AppConstants.VERSION_INFO_SUB_PATH)
	public VersionInfo getVersionUnfo(@Context UriInfo info){
		System.out.println(info.getAbsolutePath());
		List<String> s = info.getQueryParameters().get(AppConstants.PARAM_SESSION_ID);
		String [] a  =  info.getPath(true).split("/");
		String sessionId = (s == null || s.size() < 1 ? null  : s.get(0));
		System.out.println(info.getPath(true));
		RequestParams p = new RequestParams();
		p.appTobeUpdated = a[1]; p.sessionId = sessionId;
		return  controller.getVersionInfo(p);                   //VersionDescriptions.getLtfDescription().getVersionInfo();
	}
	
	
	/***
	 * Creates a new session between the client & the server!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appname}" + AppConstants.CREATE_SESSION_SUB_PATH)
	public Session getSession(@Context UriInfo info){
		/*System.out.println(info.getAbsolutePath());
		System.out.println(info.getQueryParameters().get(AppConstants.PARAM_SESSION_ID));
		System.out.println(info.getPath(true));*/
		return controller.getSession(null);
	}
	

	/***
	 * Returns a file by fileName!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appname}"  + AppConstants.FILE_SUB_PATH + "/{name}/")
	public byte [] getFileByName(@Context UriInfo info){
		System.out.println(info.getAbsolutePath());
		List<String> s = info.getQueryParameters().get(AppConstants.PARAM_SESSION_ID);
		String [] a  =  info.getPath(true).split("/");
		String sessionId = (s == null || s.size() < 1 ? null  : s.get(0));
		RequestParams p = new RequestParams();
		p.appTobeUpdated = a[1]; p.sessionId = sessionId; p.fileName = a[a.length-1];
		return   controller.getFileByName(p);    //VersionDescriptions.getLtfDescription().getFileByFileName(a[a.length-1]);
	}
	
	/***
	 * Gets the names of the available applications for update!!!
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(AppConstants.UPDATES_SUB_PATH)
	public String getAvailableUpdates(){
		return controller.getAvailableUpdates();
	}
	
	/**
	 * Returns the id's of the active sessions!!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(AppConstants.SESSIONS_SUB_PATH)
	public Set<String> getSessions(@Context UriInfo info){
		return controller.getSessions();
		
	}
	
	
	/**
	 * Returns the set with the lib files in lib directory!!!!
	 * @param info
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(AppConstants.LIBS_SUB_PATH)
	public Set<String> getLibraries(){
		return controller.getLibraries();
	}
	
	public static final IUpdateCenterController getIUpdateCenterController(){
		return controller;
	}
	
	private static final IUpdateCenterController controller = new IUpdateCenterController() {
		
		@Override
		public VersionInfo getVersionInfo(RequestParams params) {
			// TODO Auto-generated method stub
			return  UpdateCenterDispatcher.dispatchVersionInfoRequest(params.appTobeUpdated, params.sessionId);
		}
		
		@Override
		public Set<String> getSessions() {
			// TODO Auto-generated method stub
			return AppUtil.getSessionRegister().getSessionIds();
		}
		
		@Override
		public Session getSession(RequestParams params) {
			// TODO Auto-generated method stub
			return AppUtil.getSessionRegister().getSession("-1", true);
		}
		
		@Override
		public Set<String> getLibraries() {
			// TODO Auto-generated method stub
			return AppUtil.getApplicationLibFiles().getLibFiles();
		}
		
		@Override
		public byte[] getFileByName(RequestParams params) {
			// TODO Auto-generated method stub
			return   UpdateCenterDispatcher.dispatchFileRequest(params.appTobeUpdated, params.fileName, params.sessionId); 
		}
		
		@Override
		public String getAvailableUpdates() {
			// TODO Auto-generated method stub
			String s = "";
			for(CommonVersionDescription v : VersionDescriptions.getDescriptions()){
				s+=v.getVersionInfo() + "\n";
			}
			return s;
		}
	};
	
}
