package ua.training.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.ServletPath;
import ua.training.model.service.BookService;

public class DeleteBookCommand implements Command {

	private BookService bookService;

	public DeleteBookCommand(BookService bookService) {
		this.bookService = BookService.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long bookId = Long.parseLong(request.getParameter(Attribute.ID_BOOK));
		bookService.deleteBook(bookId);

		return ServletPath.ALL_BOOKS;
	}

}
