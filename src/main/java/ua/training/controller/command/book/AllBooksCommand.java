package ua.training.controller.command.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.AppConstants;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.controller.utils.PaginationManager;
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

		int page = 1;
		if (request.getParameter(Attribute.PAGE) != null) {
			page = Integer.parseInt(request.getParameter(Attribute.PAGE));
		}

		// calculate offset
		int offset = PaginationManager.getInstance().getOffset(page);
		List<Book> books = bookService.getAllBooksWithAuthorsPagination(AppConstants.LIMIT, offset);

		// calculate numberOfPages
		int numberOfBooks = bookService.countAllBooks();
		int numberOfPages = PaginationManager.getInstance().getNumberOfPages(numberOfBooks);

		request.setAttribute(Attribute.BOOKS, books);
		request.setAttribute(Attribute.NUMBER_OF_PAGES, numberOfPages);
		request.setAttribute(Attribute.CURRENT_PAGE, page);
		request.setAttribute(Attribute.LIMIT, AppConstants.LIMIT);
		return Page.ALL_BOOKS_VIEW;
	}
}
