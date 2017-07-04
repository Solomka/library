package ua.training.controller;

import javax.servlet.http.HttpServletRequest;

class CommandKeyGenerator {

	private static String CONTROLLER_PATH = ".*/controller/";
	//private static String CONTROLLER_ROLE_PATH = ".*/controller/*/";
	private static String ENTITY_ID = "\\d+";
	private static String REPLACEMENT = "";
	private static String DELIMITER = ":";

	private CommandKeyGenerator() {

	}

	 static String generateCommandKeyFromRequest(HttpServletRequest request) {
		String method = request.getMethod().toUpperCase();
		String path = request.getRequestURI().replaceAll(CONTROLLER_PATH, REPLACEMENT)
				/*.replaceAll(CONTROLLER_ROLE_PATH, REPLACEMENT)*/.replaceAll(ENTITY_ID, REPLACEMENT);

		System.out.println("PATH AFTER CORRS: " + path);
		String key = method + DELIMITER + path;
		System.out.println("String: " + key);
		return key;
	}

}
