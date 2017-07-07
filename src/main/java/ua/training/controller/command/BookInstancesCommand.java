package ua.training.controller.command;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;

public class BookInstancesCommand implements Command {

	private BookService bookService;

	public BookInstancesCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long bookId = Long.parseLong(request.getParameter(Attribute.ID_BOOK));
		Optional<Book> book = bookService.getBookById(bookId);
		request.setAttribute(Attribute.BOOK, book.get());
		return Page.BOOK_INSTANCES_VIEW;

	}

}
