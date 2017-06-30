package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Page;

public class ChangeLocaleCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setLocale(request);
		return Page.HOME;
	}

	protected void setLocale(HttpServletRequest request) {
		String lang = request.getParameter("lang");
		System.out.println("Lang: " + lang);
		if (lang.equals("EN")) {
			request.getSession().setAttribute("locale", "en_US");

		} else if (lang.endsWith("UK")) {
			request.getSession().setAttribute("locale", "uk_UA");

		} else {
			request.getSession().setAttribute("locale", "ru_RU");
		}
	}

}
