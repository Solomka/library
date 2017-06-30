package ua.training.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "/*" })
public class LocaleFilter implements Filter {

	private final static String LANG = "lang";
	private final static String LOCALE = "locale";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getParameter(LANG) != null) {
			changeLocale(req);
		}

		chain.doFilter(request, response);
	}

	private void changeLocale(HttpServletRequest request) {
		String chosenLanguage = request.getParameter(LANG);
		System.out.println("Chosen language: " + chosenLanguage);
		request.getSession(true).setAttribute(LOCALE, AppLocale.forValue(chosenLanguage));
	}

	@Override
	public void destroy() {

	}

}
