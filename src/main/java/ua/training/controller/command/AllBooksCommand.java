package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;

public class AllBooksCommand implements Command {

	private BookService bookService;

	public AllBooksCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Book> books = bookService.getBooksWithAuthors();
		request.setAttribute(Attribute.BOOKS, books);
		return Page.ALL_BOOKS_VIEW;
	}
}
