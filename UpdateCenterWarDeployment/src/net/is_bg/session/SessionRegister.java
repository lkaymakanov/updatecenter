package net.is_bg.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import net.is_bg.controller.AppUtil;
import net.is_bg.updatercenter.common.Enumerators.SESSION_STATUS;
import net.is_bg.updatercenter.common.resources.Session;

public class SessionRegister {

	private  int MAX_SESSIONS = 30;
	private  long TIME_OUT = 120*1000; //two minute session time out
	private  Map<String, Session> currentSessions = new ConcurrentHashMap<String, Session>();
	
	private SessionRegister(int maxSessions, long sessionTimeout){
		this.MAX_SESSIONS = maxSessions;
		this.TIME_OUT = sessionTimeout;
	}
	
	private  Session createSession(String ipAddress) {
		synchronized (this) {
			SessionEx session = new SessionEx();
			session.setIpAddress(ipAddress);
			session.setSessionId(UUID.randomUUID().toString());
			if (currentSessions.size() <= MAX_SESSIONS) {
				session.setStatus(SESSION_STATUS.ACTIVE);
				currentSessions.put(session.getSessionId(), session);
			} else{
				session.setSessionId("-1");
				session.setStatus(SESSION_STATUS.SERVER_BUSY);
			}
			System.out.println("Session with id " + session.getSessionId() + " created...");
			return session;
		}
	}
	
	public  Session getSession(String sessionId, boolean create, String ipAddress){
		synchronized (this){
			SessionEx s =  (SessionEx)currentSessions.get(sessionId);
			if(s == null && create) return createSession(ipAddress);
			if(s!=null) s.setLastAccesTime(System.currentTimeMillis());
			return s;
		}
	}

	public  boolean isSessionActive(String sessionId) {
		// TODO Auto-generated method stub
		synchronized (this){
			Session s =  currentSessions.get(sessionId);
			if(s == null) return false;
			return true;
		}
	}
	
	
	public void invalidate(String sessionId){
		synchronized (this){
			Session s =  currentSessions.get(sessionId);
			if(s == null) return;
			long now = System.currentTimeMillis();
			if(now - s.getLastAccesTime() >= TIME_OUT) {
				currentSessions.remove(sessionId);
				System.out.println("Session with id " + sessionId + " invalidated... Session create time = " + 
				s.getCreateTime() + " Session last access Time " + s.getLastAccesTime() + " Expire Time  =  " +  now);
			}
		}
	}

	public  Set<String> getSessionIds(){
		synchronized (this){
			return currentSessions.keySet();
		}
	}
	
	
	public  List<Session> getSessions(){
		List<Session> s = new  ArrayList<Session>();
		synchronized (this){
			for(Session ss : currentSessions.values()){
				Session sss =new  Session();
				sss.setIpAddress(ss.getIpAddress());
				sss.setSessionId(ss.getSessionId());
				s.add(sss);
			}
		}
		return s;
	}
	
	
	
	
	
	/**
	 * Create a session register with maxSession count, the session time out !!! 
	 * Creates and starts as well  refreshing daemon thread altogether with the register!!!
	 * 
	 * @param maxSessions
	 * @param sessionTimeout
	 * @return
	 */
	public static SessionRegister createSessionRegister(int maxSessions, long sessionTimeOut, TimeUnit unit){
		SessionRegister sessionRegister =   new  SessionRegister(maxSessions, unit.toMillis(sessionTimeOut));
		SessionRefresher.startSessionRefresher(sessionRegister, unit.toMillis(sessionTimeOut) - 30, TimeUnit.MILLISECONDS);
		return sessionRegister;
	}
	
	
	private static class SessionEx extends Session{
		@Override
		public void setLastAccesTime(long lastAccesTime) {
			// TODO Auto-generated method stub
			super.setLastAccesTime(lastAccesTime);
		}
	}
	
	
	public static  void main(String [] arg){
		for(int i =0 ; i< 120; i++){
			AppUtil.getSessionRegister().getSession("-1", true, "");
		}
	}
	
}
