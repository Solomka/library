package ua.training.controller.command.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
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
		// List<Book> books = bookService.getAllBooksWithAuthors();

				int page = 1;
				if (request.getParameter(Attribute.PAGE) != null) {
					page = Integer.parseInt(request.getParameter(Attribute.PAGE));
				}

				int offset = PaginationManager.getInstance().getOffset(page);
				List<Book> books = bookService.getAllBooksWithAuthorsPagination(AppConstants.LIMIT, offset);

				int numberOfBooks = bookService.countAllBooks();
				int numberOfPages = PaginationManager.getInstance().getNumberOfPages(numberOfBooks);

				request.setAttribute(Attribute.BOOKS, books);
				request.setAttribute(Attribute.NUMBER_OF_PAGES, numberOfPages);
				request.setAttribute(Attribute.CURRENT_PAGE, page);
				return Page.ALL_BOOKS_VIEW;
	}
}
