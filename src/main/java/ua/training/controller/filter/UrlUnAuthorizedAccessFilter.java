package ua.training.controller.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.session.SessionManager;
import ua.training.locale.Message;
import ua.training.locale.MessageUtils;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

@WebFilter(urlPatterns = { "/controller/librarian/*", "/controller/reader/*" })
public class UrlUnAuthorizedAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(UrlUnAuthorizedAccessFilter.class);
	private static String UNAUTHORIZED_ACCESS = "Unauthorized access to the resource: ";
	private static String ERROR_MESSAGE_ENCODING = "UTF-8";

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
			httpResponse.sendRedirect(toHomePageWithErrorMessage(httpRequest.getContextPath()));
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

	private String toHomePageWithErrorMessage(String contextPath) throws UnsupportedEncodingException {
		return new StringBuffer(contextPath).append(ServletPath.HOME).append(getErrorMessageURLParam()).toString();
	}

	private String getErrorMessageURLParam() throws UnsupportedEncodingException {
		return new StringBuffer(MessageUtils.INTERROGATION_MARK).append(Attribute.GENERAL_ERROR).append(MessageUtils.EQUALITY_SIGN)
				.append(URLEncoder.encode(Message.UNAUTHORIZED_ACCESS_ERROR, ERROR_MESSAGE_ENCODING)).toString();
	}

}
