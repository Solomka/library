package ua.training.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.Command;

class CommandFactory {

	private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("allBooks", new AllBooksCommand());
	}

	static Command getCommand(HttpServletRequest request) {
		Command command = commands.get(request.getParameter("command"));
		return command;
	}

}
