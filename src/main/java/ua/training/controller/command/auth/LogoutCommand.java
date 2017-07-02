package ua.training.controller.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.command.Command;
import ua.training.controller.constants.Page;
import ua.training.controller.session.SessionManager;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionManager.invalidateSession(request.getSession());
		return Page.LOGIN_VIEW;
	}

}
