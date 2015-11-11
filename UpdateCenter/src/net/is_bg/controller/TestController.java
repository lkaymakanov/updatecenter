package net.is_bg.controller;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.cc.rest.controller.SingleValue;

@Path("/test")
public class TestController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHello(@Context UriInfo info, @QueryParam("name") @DefaultValue("Lubo") String name) {
		byte [] bytes = new byte[256];
		for(int i = 0; i < 256; i++){
			bytes[i] = (byte)(i-128);
		}
		return Response.ok(new SingleValue(info, "", bytes.toString()))
	       .header("Access-Control-Allow-Origin", "*")
	       .header("Content-Type", "application/json; charset=utf-8")
	       .build();
	}
	
}
