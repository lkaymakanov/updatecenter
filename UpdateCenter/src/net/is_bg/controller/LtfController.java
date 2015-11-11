package net.is_bg.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.is_bg.session.SessionRegister;
import net.is_bg.updatercenter.common.AppConstants;
import net.is_bg.updatercenter.common.resources.ClientInfo;
import version.VersionDescriptions;

import com.cc.rest.controller.SingleResource;
import com.cc.rest.controller.SingleValue;

import file.splitter.ByteChunk;

@Path(AppConstants.PATH_LTF)
public class LtfController {
	//create the session register
	private SessionRegister sessionRegister = SessionRegister.createSessionRegister(20, 120*1000);
	
	@GET
	@Path(AppConstants.SUBPATH_CURRENT_VERSION)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newVersionInfo(@Context UriInfo info) {
		return Response.ok(new SingleResource(info, "", VersionDescriptions.getLtfDescription().getVersionInfo())).build();
	}
	
	@GET
	@Path(AppConstants.SUBPATH_GET_SESSION)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSession(@Context UriInfo info) {
		return Response.ok(new SingleResource(info, "", sessionRegister.getSession(null, true))).build();
	}
	
	@GET
	@Path(AppConstants.SUBPATH_GET_CHUNK_NO + AppConstants.PATH_ARAM_CHUNK_NO)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChunkNo(@Context UriInfo info, @PathParam(AppConstants.PARAM_CHUNK_NO) String chunkNoS, @QueryParam(AppConstants.PARAM_SESSION_ID) String sessionId)  throws Throwable{
		//Check if there is session
		sessionRegister.isSessionActive(sessionId);
		long chunkNo = Long.valueOf(chunkNoS);
		System.out.println("Requested chunk no = " + chunkNo);
		ByteChunk chunk = new ByteChunk();
		if(chunkNo < VersionDescriptions.getLtfDescription().getVersionInfo().chunksNumber && chunkNo > -1){
			chunk.buffer  = VersionDescriptions.getLtfDescription().getChunk(chunkNo);
			chunk.size = (int)VersionDescriptions.getLtfDescription().getChunkSize((int)chunkNo);
		}
		return Response.ok(new SingleResource(info, "", chunk)).build();
	}
	
	
	
	@GET
	@Path(AppConstants.SUBPATH_GET_FILE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFileByName(@Context UriInfo info,  @QueryParam(AppConstants.PARAM_FILE_NAME) String fileName, @QueryParam(AppConstants.PARAM_SESSION_ID) String sessionId)  throws Throwable{
		//Check if there is session
		sessionRegister.isSessionActive(sessionId);
		ByteChunk chunk = new ByteChunk();
		chunk.buffer = VersionDescriptions.getLtfDescription().getFileByFileName(fileName);
		chunk.size =  chunk.buffer == null ? 0 : chunk.buffer.length;
		return Response.ok(new SingleResource(info, "", chunk)).build();
	}
	
	
	@GET
	@Path(AppConstants.SUBPATH_IS_SESSION_ACTIVE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFileByName(@Context UriInfo info,  @QueryParam(AppConstants.PARAM_SESSION_ID) String sessionId){
		return Response.ok(new SingleResource(info, "", sessionRegister.isSessionActive(sessionId))).build();
	}
	
	
	
	@POST
	@Path(AppConstants.SUBPATH_UPDATE_CLIENT_INFO)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateClientInfo(@Context UriInfo info, ClientInfo clientInfo){
		//Update into DB
		return Response.ok(new SingleValue(info, "", "OK")).build();
	}
	
	
	
}

