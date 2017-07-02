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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.ServletPath;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

@WebFilter(urlPatterns = { "/*/librarian/*, /*/user/*" })
public class UrlAuthorizedAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(UrlAuthorizedAccessFilter.class);
	private final static String ACCESS_DENIED = "Access denied for page: ";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		if (!isUserLoggedIn(httpRequest)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ServletPath.LOGIN);
			logInfoAboutAccessDenied(httpRequest.getRequestURI());
			return;
		}

		User user = getUserFromSession(httpRequest.getSession());

		if (isUserRoleInvalidForRequestedPage(httpRequest, user)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ServletPath.HOME);
			logInfoAboutAccessDenied(httpRequest.getRequestURI());
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);

	}

	@Override
	public void destroy() {

	}

	private boolean isUserLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute(Attribute.USER) != null;
	}

	private User getUserFromSession(HttpSession session) {
		return (User) session.getAttribute(Attribute.USER);
	}

	private boolean isUserRoleInvalidForRequestedPage(HttpServletRequest request, User user) {
		return (isReaderPage(request) && !user.getRole().equals(Role.READER)
				|| (isLibrarianPage(request) && !user.getRole().equals(Role.LIBRARIAN)));
	}

	private boolean isReaderPage(HttpServletRequest request) {
		return request.getRequestURI().contains("reader");
	}

	private boolean isLibrarianPage(HttpServletRequest request) {
		return request.getRequestURI().contains("librarian");
	}

	private void logInfoAboutAccessDenied(String uri) {
		LOGGER.info(ACCESS_DENIED + uri);
	}

}
