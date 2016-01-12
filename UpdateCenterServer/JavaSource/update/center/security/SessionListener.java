package update.center.security;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public SessionListener() {

	}

	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Current Session created : " + event.getSession().getId() + " at " + new Date());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// get the destroying session...
		HttpSession session = event.getSession();
		System.out.println("Current Session destroyed :" + session.getId() + " Logging out user...");

		/*
		 * nobody can reach user data after this point because session is
		 * invalidated already. So, get the user data from session and save its
		 * logout information before losing it. User's redirection to the
		 * timeout page will be handled by the SessionTimeoutFilter.
		 */

		// Only if needed

		try {
			prepareLogoutInfoAndLogoutActiveUser(session);
			//AbstractManagedBean.getServiceLocator().getSessionDao().deleteSession(session.getId());
		} catch (Exception e) {
			System.out.println("Error while logging out at session destroyed : " + e.getMessage());
		}
	}

	/**
	 * Clean your logout operations.
	 */

	public void prepareLogoutInfoAndLogoutActiveUser(HttpSession httpSession) {
		// Only if needed
	}
}