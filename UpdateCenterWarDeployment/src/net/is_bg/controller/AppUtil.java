package net.is_bg.controller;

import java.util.concurrent.TimeUnit;

import net.is_bg.session.SessionRegister;

public class AppUtil {

	private static  SessionRegister sessionRegister;
	
	public static SessionRegister getSessionRegister(){
		if(sessionRegister == null) sessionRegister = SessionRegister.createSessionRegister(30, 2, TimeUnit.MINUTES);
		return sessionRegister;
	}
	
	public static void createSessionRegister(int maxSessions, long sessionTimeOut, TimeUnit timeUnit){
		if(sessionRegister ==null) sessionRegister = SessionRegister.createSessionRegister(maxSessions, sessionTimeOut, timeUnit);
	}
	
	public static ApplicationLibFiles getApplicationLibFiles(){
		return ApplicationLibFiles.getApplicationLibFiles();
	}
}
