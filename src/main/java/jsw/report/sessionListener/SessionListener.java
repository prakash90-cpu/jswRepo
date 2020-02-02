package jsw.report.sessionListener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	private int sessionCount = 0;

	public void sessionCreated(HttpSessionEvent event) {
		synchronized (this) {
			sessionCount++;
		}

		System.out.println("Session Created: " + event.getSession().getId());
		System.out.println("Total Sessions: " + sessionCount);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		synchronized (this) {
			sessionCount--;
		}
		
		HttpSession session= event.getSession();
		session.invalidate();
		
		session.setAttribute("sid", null);
		session.setAttribute("menuNames", null);
		session.setAttribute("loggedUser", null);
		session.setAttribute("userName", null);
		session.setAttribute("UserId", null);
		session.setAttribute("groupList", null);
		session.setAttribute("settingValue", null);
		session.setAttribute("agingList", null);
		session.setAttribute("agingList_other", null);
		
		System.out.println("Session Destroyed: " + session.getId());
		System.out.println("Total Sessions: " + sessionCount);
		session=null;
	}
}
