package ua.training.controller.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.session.SessionManager;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.locale.Message;

@WebFilter(urlPatterns = { "/controller/librarian/*", "/controller/reader/*" })
public class UrlUnauthorizedAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(UrlUnauthorizedAccessFilter.class);
	private static final String UNAUTHORIZED_ACCESS = "Unauthorized access to the resource: ";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		User user = SessionManager.getUserFromSession(httpRequest.getSession());

		if (!isUserRegistered(user) || !isUserAuthorizedForResource(httpRequest.getRequestURI(), user)) {
			logInfoAboutUnauthorizedAccess(httpRequest.getRequestURI());
			HttpWrapper httpWrapper = new HttpWrapper(httpRequest, httpResponse);
			Map<String, String> urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.UNAUTHORIZED_ACCESS_ERROR);
			RedirectionManager.redirectWithParams(httpWrapper, ServletPath.HOME, urlParams);
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

	private boolean isUserRegistered(User user) {
		return user != null;

	}

	private boolean isUserAuthorizedForResource(String servletPath, User user) {
		return (isReaderPage(servletPath) && user.getRole().equals(Role.READER)
				|| (isLibrarianPage(servletPath) && user.getRole().equals(Role.LIBRARIAN)));
	}

	private boolean isReaderPage(String requestURI) {
		return requestURI.contains(Role.READER.getValue());
	}

	private boolean isLibrarianPage(String requestURI) {
		return requestURI.contains(Role.LIBRARIAN.getValue());
	}
	
	private void logInfoAboutUnauthorizedAccess(String uri) {
		LOGGER.info(UNAUTHORIZED_ACCESS + uri);
	}
}
