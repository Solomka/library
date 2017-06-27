package ua.training.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.ChangeLocaleCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.GetLoginPageCommand;
import ua.training.controller.command.LoginCommand;
import ua.training.controller.command.LogoutCommand;

class CommandFactory {

	private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("allBooks", new AllBooksCommand());
		commands.put("changeLocale", new ChangeLocaleCommand());
		commands.put("login", new LoginCommand());
		commands.put("getLoginPage", new GetLoginPageCommand());
		commands.put("logout", new LogoutCommand());
	}

	static Command getCommand(HttpServletRequest request) {
		String str = request.getRequestURI().replaceAll(".*/", "");
		Command command = commands.getOrDefault(str, new AllBooksCommand());
		return command;
	}

}
