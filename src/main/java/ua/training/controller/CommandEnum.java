package ua.training.controller;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.ChangeLocaleCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.GetLoginPageCommand;
import ua.training.controller.command.LoginCommand;
import ua.training.controller.command.LogoutCommand;

public enum CommandEnum {

	HOME {
		{
			this.key = "GET:";
			this.command = new HomeCommand();
		}
	},
	GET_LOGINPAGE {
		{
			this.key = "GET:login";
			this.command = new GetLoginPageCommand();
		}
	},
	CHANGE_LOCALE {
		{
			this.key = "GET:locale";
			this.command = new ChangeLocaleCommand();
		}
	},
	LOGIN {
		{
			this.key = "POST:login";
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.key = "GET:logout";
			this.command = new LogoutCommand();
		}
	},
	ALL_BOOKS {
		{
			this.key = "GET:books";
			this.command = new AllBooksCommand();

		}

	};

	private final static Command DEFAULT_COMMAND = HOME.getCommand();
	String key;
	Command command;

	public Command getCommand() {
		return command;
	}

	public String getKey() {
		return key;
	}

	public static Command getDefault() {
		return DEFAULT_COMMAND;
	}

	public static Command getCommand(String key) {
		for (final CommandEnum command : CommandEnum.values()) {
			if (command.getKey().equals(key)) {
				return command.getCommand();
			}
		}
		return getDefault();
	}

}
