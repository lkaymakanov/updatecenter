package update.center.init;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * A class that manages HttpSessions in the application!!!
 * @author Lubo
 *
 */
public class ApplicationSessionManager  {

    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();        

    /**
     * Adds session to the session map!!!
     * @param session
     */
    public static void addSession(HttpSession session) {
    	if(session == null) return;
        sessions.put(session.getId(), session);
    }

    /**
     * Destroys a session & removes it from session map!!!
     * @param session
     */
    public static void destroySession(HttpSession session) {
    	if(session == null) return;
		HttpSession s =  sessions.remove(session.getId());
		if(s!= null) s.invalidate();
    }

    /**
     * Destroys a session by id!!!
     * @param sessionId
     */
    public static void  destroySession(String sessionId) {
		HttpSession session = sessions.get(sessionId);
		destroySession(session);
    }
    
    /**
     * Just removes session from session map!!!
     */
    public static HttpSession removeSessionFromMap(HttpSession session){
    	if(session == null) return null;
    	return removeSessionFromMap(session.getId());
    	
    }
    
    
    /**
     * Just removes session from session map!!!
     */
    public static HttpSession removeSessionFromMap(String id){
		HttpSession session = getSession(id);
		sessions.remove(id);
		return session;
    }
    
    public static HttpSession getSession(String id){
    	return sessions.get(id);
    }
    
    public static void fillSessions(List<HttpSessionEx> sessions){
    	for(HttpSession ss : ApplicationSessionManager.sessions.values()){
    		sessions.add((HttpSessionEx)ss);
    	}
    }
    
    public static List<HttpSessionEx> getSessions(){
    	List<HttpSessionEx> s = new ArrayList<HttpSessionEx>();
    	for(HttpSession ss : sessions.values()){
    		s.add((HttpSessionEx)ss);
    	}
    	return s;
    }

}
