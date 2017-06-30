package ua.training.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.GetLoginPageCommand;
import ua.training.controller.command.LoginCommand;
import ua.training.controller.command.LogoutCommand;

class CommandFactory {

	private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("GET:", new HomeCommand());
		commands.put("GET:login", new GetLoginPageCommand());
		commands.put("POST:login", new LoginCommand());
		commands.put("GET:logout", new LogoutCommand());
		commands.put("GET:books", new AllBooksCommand());		
	}

	static Command getCommand(HttpServletRequest request) {

		String method = request.getMethod().toUpperCase();
		String path = request.getRequestURI();
		path = path.replaceAll(".*/controller/", "").replaceAll(".*/controller/*/", "").replaceAll("\\d+", "");

		String key = method + ":" + path;
		System.out.println("String: " + key);

		Command command = commands.getOrDefault(key, new HomeCommand());
		return command;
	}

}
