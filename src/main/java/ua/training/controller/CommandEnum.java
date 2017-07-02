package ua.training.controller;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.command.auth.PostLoginCommand;
import ua.training.controller.command.i18n.ChangeLocaleCommand;
import ua.training.model.service.BookService;
import ua.training.model.service.UserService;

enum CommandEnum {

	HOME {
		{
			this.key = "GET:";
			this.command = new HomeCommand();
		}
	},
	
	CHANGE_LOCALE {
		{
			this.key = "GET:locale";
			this.command = new ChangeLocaleCommand();
		}
	},
	GET_LOGIN {
		{
			this.key = "GET:login";
			this.command = new GetLoginCommand();
		}
	},
	POST_LOGIN {
		{
			this.key = "POST:login";
			this.command = new PostLoginCommand(UserService.getInstance());
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
			this.command = new AllBooksCommand(BookService.getInstance());

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
