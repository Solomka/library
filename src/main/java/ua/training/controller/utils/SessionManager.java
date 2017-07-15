package ua.training.controller.utils;

import javax.servlet.http.HttpSession;

import ua.training.constants.Attribute;
import ua.training.entity.User;

public class SessionManager {

	private SessionManager() {
	}

	private static final class Holde {
		static final SessionManager INSTANCE = new SessionManager();
	}

	public static SessionManager getInstance() {
		return Holde.INSTANCE;
	}

	public boolean isUserLoggedIn(HttpSession session) {
		return session.getAttribute(Attribute.USER) != null;
	}

	public void addUserToSession(HttpSession session, User user) {
		session.setAttribute(Attribute.USER, user);
	}

	public User getUserFromSession(HttpSession session) {
		return (User) session.getAttribute(Attribute.USER);
	}

	public void invalidateSession(HttpSession session) {
		session.invalidate();
	}
}
