package ua.training.controller;

import org.apache.log4j.Logger;

import ua.training.controller.command.Command;

class CommandFactory {

	private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

	private CommandFactory() {

	}

	static Command getCommand(String commendKey) {
		Command command = CommandEnum.getCommand(commendKey);
		return command;
	}

}
