package net.is_bg.updatercenter.common;

import com.cc.rest.client.enumerators.IPARAM;
import com.cc.rest.client.enumerators.IREST_PATH;

public class Enumerators {
	public enum REST_PATH implements IREST_PATH{
		TEST(AppConstants.PATH_TEST),
		LTF(AppConstants.PATH_LTF),
		CURRENT_VERSION(AppConstants.SUBPATH_CURRENT_VERSION),
		GET_CHUNK_NO(AppConstants.SUBPATH_GET_CHUNK_NO),
		UPDATE_CLIENT_INFO(AppConstants.SUBPATH_UPDATE_CLIENT_INFO),
		GET_SESSION(AppConstants.SUBPATH_GET_SESSION),
		GET_FILE(AppConstants.SUBPATH_GET_FILE),
		IS_SESSION_ACTIVE(AppConstants.SUBPATH_IS_SESSION_ACTIVE);
		private String path;
		private REST_PATH(String path){
			this.path = path;
		}
		@Override
		public String getPath() {
			return path;
		}
	}
	
	public enum PARAMS implements IPARAM {
		CHUNK_NO(AppConstants.PARAM_CHUNK_NO),
		SESSION_ID(AppConstants.PARAM_SESSION_ID),
		FILE_NAME(AppConstants.PARAM_FILE_NAME);
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
}
