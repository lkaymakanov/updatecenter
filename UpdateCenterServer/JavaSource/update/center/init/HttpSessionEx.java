package update.center.init;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import update.center.security.User;

@SuppressWarnings("deprecation")
public class HttpSessionEx implements HttpSession {
	
	private HttpSession session;
	private String ipAddress;
	private User user;
	
	public HttpSessionEx(HttpSession session){
		this.session = session;
	}
	

	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return session.getCreationTime();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return session.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return session.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		// TODO Auto-generated method stub
		session.setMaxInactiveInterval(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return session.getMaxInactiveInterval();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return session.getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return session.getAttribute(name);
	}

	@Override
	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return session.getValue(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return session.getAttributeNames();
	}

	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return session.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		session.setAttribute(name, value);
	}

	@Override
	public void putValue(String name, Object value) {
		// TODO Auto-generated method stub
		session.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		session.removeAttribute(name);
	}

	@Override
	public void removeValue(String name) {
		// TODO Auto-generated method stub
		session.removeValue(name);
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		session.invalidate();
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return session.isNew();
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getCreateDateTime(){
		return new Date(getCreationTime());
	}

}
