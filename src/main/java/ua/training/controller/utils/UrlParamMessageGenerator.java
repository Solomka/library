package ua.training.controller.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ua.training.locale.MessageUtils;

public final class UrlParamMessageGenerator {

	private static String MESSAGE_ENCODING = "UTF-8";

	private UrlParamMessageGenerator() {

	}

	public static String getMessageURLParam(String messageParamName, String message)
			throws UnsupportedEncodingException {
		return new StringBuffer(MessageUtils.INTERROGATION_MARK).append(messageParamName)
				.append(MessageUtils.EQUALITY_SIGN).append(URLEncoder.encode(message, MESSAGE_ENCODING)).toString();
	}

}
