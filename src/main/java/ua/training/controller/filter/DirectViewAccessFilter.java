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
import ua.training.locale.Message;

@WebFilter(urlPatterns = { "/views/*" })
public class DirectViewAccessFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(DirectViewAccessFilter.class);
	private static String UNAUTHORIZED_ACCESS = "Unauthorized access to the resource: ";
	private static String ERROR_MESSAGE_ENCODING = "UTF-8";

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		logInfoAboutUnauthorizedAccess(httpRequest.getRequestURI());
		httpResponse.sendRedirect(toHomePageWithErrorMessage(httpRequest.getContextPath()));
	}

	public void destroy() {
	}

	private String toHomePageWithErrorMessage(String contextPath) throws UnsupportedEncodingException {
		return new StringBuffer(contextPath).append(ServletPath.HOME).append(getErrorMessageURLParam()).toString();
	}

	private String getErrorMessageURLParam() throws UnsupportedEncodingException {
		return new StringBuffer("?").append(Attribute.GENERAL_ERROR).append("=")
				.append(URLEncoder.encode(Message.DIRECT_VIEW_ACCESS_ERROR, ERROR_MESSAGE_ENCODING)).toString();
	}

	private void logInfoAboutUnauthorizedAccess(String uri) {
		LOGGER.info(UNAUTHORIZED_ACCESS + uri);
	}
}
