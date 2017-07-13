package ua.training.controller;

import ua.training.controller.command.AllAuthorsCommand;
import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.command.AllOrdersCommand;
import ua.training.controller.command.AllReadersCommand;
import ua.training.controller.command.ReturnOrderToReadingRoomCommand;
import ua.training.controller.command.ToReadingRoomOrdersCommand;
import ua.training.controller.command.BookInstancesCommand;
import ua.training.controller.command.Command;
import ua.training.controller.command.CreateOrderCommand;
import ua.training.controller.command.FulfilOrderCommand;
import ua.training.controller.command.GetAddAuthorCommand;
import ua.training.controller.command.GetAddBookCommand;
import ua.training.controller.command.GetAddReaderCommand;
import ua.training.controller.command.GetBookByInstnaceIdCommand;
import ua.training.controller.command.GetChangePasswordCommand;
import ua.training.controller.command.GetReaderByIdCommand;
import ua.training.controller.command.HomeCommand;
import ua.training.controller.command.IssueOrderCommand;
import ua.training.controller.command.OutstandingOrdersCommand;
import ua.training.controller.command.PageNotFoundCommand;
import ua.training.controller.command.PostAddAuthorCommand;
import ua.training.controller.command.PostAddBookCommand;
import ua.training.controller.command.PostAddBookInstanceCommand;
import ua.training.controller.command.PostAddReaderCommand;
import ua.training.controller.command.PostChangePasswordCommand;
import ua.training.controller.command.ReturnOrderCommand;
import ua.training.controller.command.SearchBookByAuthorCommand;
import ua.training.controller.command.SearchBookByTitleCommand;
import ua.training.controller.command.SearchOrderByReaderCardNumberCommand;
import ua.training.controller.command.UnfulfilledOrdersCommand;
import ua.training.controller.command.auth.GetLoginCommand;
import ua.training.controller.command.auth.LogoutCommand;
import ua.training.controller.command.auth.PostLoginCommand;
import ua.training.controller.command.i18n.ChangeLocaleCommand;
import ua.training.service.AuthorService;
import ua.training.service.BookInstanceService;
import ua.training.service.BookOrderService;
import ua.training.service.BookService;
import ua.training.service.UserService;

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
	GET_BOOK_BY_INSTANCE_ID{
		{
			this.key = "GET:book";
			this.command = new GetBookByInstnaceIdCommand(BookService.getInstance());
		}
	},
	BOOK_INSTANCES {
		{
			this.key = "GET:bookInstances";
			this.command = new BookInstancesCommand(BookService.getInstance());
		}
	},
	ADD_BOOK_INSTANCE {
		{
			this.key = "POST:addBookInstance";
			this.command = new PostAddBookInstanceCommand(BookInstanceService.getInstance());
		}
	},
	GET_ADD_BOOK {
		{
			this.key = "GET:librarian/addBook";
			this.command = new GetAddBookCommand(AuthorService.getInstance());
		}
	},
	POST_ADD_BOOK {
		{
			this.key = "POST:librarian/addBook";
			this.command = new PostAddBookCommand(BookService.getInstance(), AuthorService.getInstance());
		}
	},
	SEARCH_BOOK_BY_TITLE {
		{
			this.key = "POST:searchBookByTitle";
			this.command = new SearchBookByTitleCommand(BookService.getInstance());
		}
	},
	SEARCH_BOOK_BY_AUTHOR {
		{
			this.key = "POST:searchBookByAuthor";
			this.command = new SearchBookByAuthorCommand(BookService.getInstance());
		}
	},
	ALL_AUTHORS {
		{
			this.key = "GET:librarian/authors";
			this.command = new AllAuthorsCommand(AuthorService.getInstance());
		}
	},
	GET_ADD_AUTHOR {
		{
			this.key = "GET:librarian/addAuthor";
			this.command = new GetAddAuthorCommand();
		}
	},
	POST_ADD_AUTHOR {
		{
			this.key = "POST:librarian/addAuthor";
			this.command = new PostAddAuthorCommand(AuthorService.getInstance());
		}
	},
	ALL_READERS {
		{
			this.key = "GET:librarian/readers";
			this.command = new AllReadersCommand(UserService.getInstance());
		}
	},
	GET_READER_BY_ID{
		{
			this.key = "GET:librarian/reader";
			this.command = new GetReaderByIdCommand(UserService.getInstance());
		}
	},
	GET_ADD_READER {
		{
			this.key = "GET:librarian/addReader";
			this.command = new GetAddReaderCommand();
		}
	},
	POST_ADD_READER {
		{
			this.key = "POST:librarian/addReader";
			this.command = new PostAddReaderCommand(UserService.getInstance());
		}
	},
	GET_CHANGE_PASSWORD {
		{
			this.key = "GET:reader/changePassword";
			this.command = new GetChangePasswordCommand();
		}
	},
	POST_CHANGE_PASSWORD {
		{
			this.key = "POST:reader/changePassword";
			this.command = new PostChangePasswordCommand(UserService.getInstance());
		}
	},
	ALL_READER_ORDERS {
		{
			this.key = "GET:reader/orders";
			this.command = new AllOrdersCommand(BookOrderService.getInstance());
		}
	},
	ALL_ORDERS {
		{
			this.key = "GET:librarian/orders";
			this.command = new AllOrdersCommand(BookOrderService.getInstance());
		}
	},
	CREATE_ORDER {
		{
			this.key = "GET:reader/createOrder";
			this.command = new CreateOrderCommand(BookOrderService.getInstance());

		}
	},
	UNFULFILLED_ORDERS{
		{
			this.key = "GET:librarian/orders/unfulfilled";
			this.command = new UnfulfilledOrdersCommand(BookOrderService.getInstance());			
		}
	},
	OUTSTANDING_ORDERS {
		{
			this.key = "GET:librarian/orders/outstanding";
			this.command = new OutstandingOrdersCommand(BookOrderService.getInstance());

		}
	},
	TO_READING_ROOM_ORDERS{
		{
			this.key = "GET:librarian/orders/toRreadingRoom";
			this.command = new ToReadingRoomOrdersCommand(BookOrderService.getInstance());			
		}
	},
	BACK_ORDER_TO_READING_ROOM{
		{
			this.key = "GET:librarian/orders/backToReadingRoom";
			this.command = new ReturnOrderToReadingRoomCommand(BookOrderService.getInstance());			
		}
	},
	SEARCH_ORDER_BY_READER_CARD_NUMBER{
		{
			this.key = "GET:librarian/orders/readerCardNumber";
			this.command = new SearchOrderByReaderCardNumberCommand(BookOrderService.getInstance());
		}
	},
	FULFIL_ORDER{
		{
			this.key = "GET:librarian/orders/fulfil";
			this.command = new FulfilOrderCommand(BookOrderService.getInstance());			
		}
	},
	ISSUE_ORDER{
		{
			this.key = "GET:librarian/orders/issue";
			this.command = new IssueOrderCommand(BookOrderService.getInstance());			
		}
	},
	RETURN_ORDER{
		{
			this.key = "GET:librarian/orders/return";
			this.command = new ReturnOrderCommand(BookOrderService.getInstance());			
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
