package net.is_bg.updatercenter.common;

import com.cc.rest.client.enumerators.IPARAM;
import com.cc.rest.client.enumerators.IREST_PATH;

public class Enumerators {
	public enum REST_PATH implements IREST_PATH{
		TEST(AppConstants.PATH_TEST),
		APP(AppConstants.PATH_APPLICATION),
		LTF(AppConstants.PATH_LTF);
		
		
		private String path;
		
		private REST_PATH(String path){
			this.path = path;
		}
		@Override
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}
	
	
	public enum PARAMS implements IPARAM {
		SESSION_ID(AppConstants.PARAM_SESSION_ID);
		private PARAMS(String stringValue){
			this.stringValue = stringValue;
		}
		private String stringValue;
		@Override
		public String getStringValue() {
			return stringValue;
		}
		
	}
	
	public enum SESSION_STATUS{
		ACTIVE, 
		INACTIVE,
		SERVER_BUSY;
	}
	
	
	public static IREST_PATH getVersionSubPath(String appName){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/" + appName + "/versioninfo/");    
		return p;
	}
	
	public static IREST_PATH getFileSubPath(String appName, String fName){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/" + appName + "/file/" + fName);
		return p;
	}
	
	
	public static IREST_PATH getCreateSessionSubPath(String appName){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/" + appName + "/session/");
		return p;
	}
	
	public static IREST_PATH getSessionsSubPath(){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/sessions/");
		return p;
	}
	
	public static IREST_PATH getUpdatesSubPath(){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/updates/");
		return p;
	}
	
	public static IREST_PATH getLibsSubPath(){
		SELECTIVE_PATHS p = new  SELECTIVE_PATHS();
		p.setPath("/libs/");
		return p;
	}
	
	
	public static class SELECTIVE_PATHS implements IREST_PATH{
		private String path;
		void setPath(String path){
			this.path = path;
		}
		
		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return path;
		}
	}
	
}
