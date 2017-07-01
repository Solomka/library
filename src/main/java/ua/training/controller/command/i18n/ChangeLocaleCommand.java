package ua.training.controller.command.i18n;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.command.Command;
import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;

public class ChangeLocaleCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setLocale(request);
		return Page.HOME_VIEW;
	}

	private void setLocale(HttpServletRequest request) {
		final String selectedLanguage = request.getParameter(Attribute.LANG);
		request.getSession().setAttribute(Attribute.LOCALE, AppLocale.forValue(selectedLanguage));
	}
}
