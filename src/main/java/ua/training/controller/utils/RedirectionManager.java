package ua.training.controller.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.locale.MessageUtils;

public final class RedirectionManager {

	public static String REDIRECTION = "REDIRECTION";
	private static String MESSAGE_ENCODING = "UTF-8";

	private RedirectionManager() {
	}

	public static void redirectWithParamMessage(HttpServletRequest request, HttpServletResponse response, String path,
			String paramName, String paramValue) throws UnsupportedEncodingException, IOException {
		response.sendRedirect(RedirectionManager.generateResourcePath(request, path)
				+ RedirectionManager.getMessageURLParam(paramName, paramValue));

	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		response.sendRedirect(RedirectionManager.generateResourcePath(request, path));
	}

	private static String generateResourcePath(HttpServletRequest request, String path) {
		// String resourcePath = new
		// StringBuffer(request.getContextPath()).append(request.getServletPath()).append(path).toString();
		return new StringBuffer(request.getContextPath()).append(request.getServletPath()).append(path).toString();

	}

	public static String getMessageURLParam(String paramName, String paramValue) throws UnsupportedEncodingException {
		return new StringBuffer(MessageUtils.INTERROGATION_MARK).append(paramName).append(MessageUtils.EQUALITY_SIGN)
				.append(URLEncoder.encode(paramValue, MESSAGE_ENCODING)).toString();
	}

}
