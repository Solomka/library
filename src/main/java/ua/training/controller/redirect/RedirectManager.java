package ua.training.controller.redirect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import ua.training.locale.MessageUtils;

public final class RedirectManager {

	private static String MESSAGE_ENCODING = "UTF-8";
	private static String REDIRECTION ="REDIRECTION";

	private RedirectManager() {

	}

	public static void redirectWithParams(HttpServletRequest request){
		
	}
	public static void redirect(HttpServletRequest request){
		
	}
	public static String getMessageURLParam(String messageParamName, String message)
			throws UnsupportedEncodingException {
		return new StringBuffer(MessageUtils.INTERROGATION_MARK).append(messageParamName)
				.append(MessageUtils.EQUALITY_SIGN).append(URLEncoder.encode(message, MESSAGE_ENCODING)).toString();
	}

}
