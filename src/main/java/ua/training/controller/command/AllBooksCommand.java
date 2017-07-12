package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Book;
import ua.training.service.BookService;

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
