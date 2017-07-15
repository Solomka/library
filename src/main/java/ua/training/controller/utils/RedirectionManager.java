package ua.training.controller.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.exception.ServerException;
import ua.training.locale.MessageUtils;

public class RedirectionManager {

	public static String REDIRECTION = "REDIRECTION";
	private static String MESSAGE_ENCODING = "UTF-8";

	private RedirectionManager() {
	}

	private static final class Holder {
		static final RedirectionManager INSTANCE = new RedirectionManager();
	}

	public static RedirectionManager getInstance() {
		return Holder.INSTANCE;
	}

	public void redirectWithParams(HttpWrapper httpWrapper, String redirectionPath, Map<String, String> urlParameters)
			throws IOException {
		String urlPathWithParams = generateUrlPath(httpWrapper.getRequest(), redirectionPath)
				+ generateUrlParams(urlParameters);
		redirect(httpWrapper.getRequest(), httpWrapper.getResponse(), urlPathWithParams);
	}

	public void redirect(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			response.sendRedirect(generateUrlPath(request, path));
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	private String generateUrlPath(HttpServletRequest request, String path) {
		return new StringBuffer(request.getContextPath()).append(request.getServletPath()).append(path).toString();
	}

	public String generateUrlParams(Map<String, String> urlParameters) throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer(MessageUtils.INTERROGATION_MARK);
		for (String urlParamName : urlParameters.keySet()) {
			stringBuffer.append(urlParamName).append(MessageUtils.EQUALITY_SIGN)
					.append(URLEncoder.encode(urlParameters.get(urlParamName), MESSAGE_ENCODING))
					.append(MessageUtils.AMPERSAND);
		}
		deleteLastAmpersand(stringBuffer);
		return stringBuffer.toString();
	}

	private void deleteLastAmpersand(StringBuffer stringBuffer) {
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
	}
}
