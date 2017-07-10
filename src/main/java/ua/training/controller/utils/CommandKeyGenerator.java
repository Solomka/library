package ua.training.controller.utils;

import javax.servlet.http.HttpServletRequest;

public final class CommandKeyGenerator {

	private static String CONTROLLER_PATH = ".*/controller/";
	private static String ENTITY_ID = "\\d+";
	private static String REPLACEMENT = "";
	private static String DELIMITER = ":";

	private CommandKeyGenerator() {
	}

	public static String generateCommandKeyFromRequest(HttpServletRequest request) {
		String method = request.getMethod().toUpperCase();
		String path = request.getRequestURI().replaceAll(CONTROLLER_PATH, REPLACEMENT).replaceAll(ENTITY_ID,
				REPLACEMENT);
		String key = method + DELIMITER + path;
		System.out.println("Key: " + key);
		return key;
	}
}
