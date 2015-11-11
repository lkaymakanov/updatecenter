package net.is_bg.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.is_bg.updatercenter.common.Enumerators.SESSION_STATUS;
import net.is_bg.updatercenter.common.resources.Session;

public class SessionRegister {

	private  int MAX_SESSIONS = 30;
	private  long TIME_OUT = 120*1000; //two minute session time out
	private  Map<String, Session> currentSessions = new HashMap<String, Session>();
	
	private SessionRegister(int maxSessions, long sessionTimeout){
		this.MAX_SESSIONS = maxSessions;
		this.TIME_OUT = sessionTimeout;
	}
	
	private  Session createSession() {
		synchronized (this) {
			Session session = new Session();
			session.setSessionId(UUID.randomUUID().toString());
			if (currentSessions.size() <= MAX_SESSIONS) {
				session.setStatus(SESSION_STATUS.ACTIVE);
				currentSessions.put(session.getSessionId(), session);
			} else
				session.setSessionId("-1");
				session.setStatus(SESSION_STATUS.SERVER_BUSY);
			return session;
		}
	}
	
	public  Session getSession(String sessionId, boolean create){
		synchronized (this){
			Session s =  currentSessions.get(sessionId);
			if(s == null && create) return createSession();
			return s;
		}
	}

	public  boolean isSessionActive(String sessionId) {
		// TODO Auto-generated method stub
		synchronized (this){
			Session s =  currentSessions.get(sessionId);
			if(s == null) return false;
			if(System.currentTimeMillis() - s.getLastAccesTime() >= TIME_OUT ) {
				currentSessions.remove(sessionId);
				return false;
			}
			return true;
		}
	}

	public  Set<String> getSessionIds(){
		synchronized (SessionRegister.class){
			return currentSessions.keySet();
		}
	}
	
	/**
	 * Create a session register with maxSession count, the session time out in milliseconds!!!
	 * @param maxSessions
	 * @param sessionTimeout
	 * @return
	 */
	public static SessionRegister createSessionRegister(int maxSessions, long sessionTimeout){
		return  new  SessionRegister(maxSessions, sessionTimeout);
	}
	
	
}
