package ua.training.controller.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.locale.MessageUtils;

public final class RedirectionManager {

	public static String REDIRECTION = "REDIRECTION";
	private static String MESSAGE_ENCODING = "UTF-8";

	private RedirectionManager() {
	}

	public static void redirectWithParams(HttpWrapper httpWrapper, String redirectionPath,
			Map<String, String> urlParameters) throws IOException {
		String urlPathWithParams = RedirectionManager.generateUrlPath(httpWrapper.getRequest(), redirectionPath)
				+ RedirectionManager.generateUrlParams(urlParameters);
		redirect(httpWrapper.getRequest(), httpWrapper.getResponse(), urlPathWithParams);
	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		response.sendRedirect(RedirectionManager.generateUrlPath(request, path));
	}

	private static String generateUrlPath(HttpServletRequest request, String path) {
		return new StringBuffer(request.getContextPath()).append(request.getServletPath()).append(path).toString();
	}

	public static String generateUrlParams(Map<String, String> urlParameters) throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer(MessageUtils.INTERROGATION_MARK);
		for (String urlParamName : urlParameters.keySet()) {
			stringBuffer.append(urlParamName).append(MessageUtils.EQUALITY_SIGN)
					.append(URLEncoder.encode(urlParameters.get(urlParamName), MESSAGE_ENCODING))
					.append(MessageUtils.AMPERSAND);
		}
		deleteLastAmpersand(stringBuffer);
		return stringBuffer.toString();
	}

	private static void deleteLastAmpersand(StringBuffer stringBuffer) {
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
	}
}
