package ua.training.controller;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.BookInstancesCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.PageNotFoundCommand;
import ua.training.controller.command.GetAddBookCommand;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.PostAddBookCommand;
import ua.training.controller.command.SearchBookByAuthorCommand;
import ua.training.controller.command.SearchBookByTitleCommand;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.command.auth.PostLoginCommand;
import ua.training.controller.command.i18n.ChangeLocaleCommand;
import ua.training.model.service.AuthorService;
import ua.training.model.service.BookService;
import ua.training.model.service.UserService;

enum CommandEnum {
	PAGE_NOT_FOUND {
		{
			this.key = "GET:pageNotFound";
			this.command = new PageNotFoundCommand();
		}
	},
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

	},
	BOOK_INSTANCES{
		{
			this.key = "GET:bookInstances";
			this.command = new BookInstancesCommand(BookService.getInstance());
		}
	},
	
	/*BOOK_AUTHORS{
		{
			this.key = "GET:bookAuthors";
			this.command = new BookAuthorsCommand(AuthorService.getInstance());
			
		}
	},*/
	
	GET_ADD_BOOK{
		{
			this.key = "GET:librarian/addBook";
			this.command = new GetAddBookCommand(AuthorService.getInstance());
		}
	},
	POST_ADD_BOOK{
		{
			this.key = "POST:librarian/addBook";
			this.command = new PostAddBookCommand(BookService.getInstance(), AuthorService.getInstance());
		}
	},
	SEARCH_BOOK_BY_TITLE{
		{
			this.key = "POST:searchBookByTitle";
			this.command = new SearchBookByTitleCommand(BookService.getInstance());
		}
	},
	SEARCH_BOOK_BY_AUTHOR{
		{
			this.key = "POST:searchBookByAuthor";
			this.command = new SearchBookByAuthorCommand(BookService.getInstance());
		}
	};
	
	String key;
	Command command;

	public Command getCommand() {
		return command;
	}

	public String getKey() {
		return key;
	}

	
	public static Command getCommand(String key) {
		for (final CommandEnum command : CommandEnum.values()) {
			if (command.getKey().equals(key)) {
				return command.getCommand();
			}
		}
		return PAGE_NOT_FOUND.getCommand();
	}
}
