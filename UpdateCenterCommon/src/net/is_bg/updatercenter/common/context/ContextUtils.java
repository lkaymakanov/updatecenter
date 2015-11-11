package net.is_bg.updatercenter.common.context;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ContextUtils {

	private static Context initContext = null; 
	
	private static Context getContext(){
		if(initContext != null) return initContext;
		try{
			initContext = (Context)new InitialContext().lookup("java:/comp/env");
			return initContext;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	


	@SuppressWarnings("unchecked")
	public static <T> T getParam(String name, Class<T> type) {
		try{
			Object o = getContext().lookup(name);
			return (T)o;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getParam(String name, Class<T> type, T defaultRes) {
		try{
			Object o = getContext().lookup(name);
			return (T)o;
		}catch (Exception e) {
			// TODO: handle exception
			return defaultRes;
		}
	}
}
