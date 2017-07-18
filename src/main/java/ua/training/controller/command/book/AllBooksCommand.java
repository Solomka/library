package ua.training.controller.command.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.constants.AppConstants;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.FrontController;
import ua.training.controller.command.Command;
import ua.training.controller.utils.PaginationManager;
import ua.training.entity.Book;
import ua.training.service.BookService;

public class AllBooksCommand implements Command {

	private static final Logger LOGGER = Logger.getLogger(AllBooksCommand.class);
	
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

		// calculate offset
		int offset = PaginationManager.getInstance().getOffset(page);
		LOGGER.info("Offset: " + offset);
		List<Book> books = bookService.getAllBooksWithAuthorsPagination(AppConstants.LIMIT, offset);

		int numberOfBooks = bookService.countAllBooks();
		LOGGER.info("Number of records: " + numberOfBooks);
		
		//calculate numberOfPages
		int numberOfPages = PaginationManager.getInstance().getNumberOfPages(numberOfBooks);
		LOGGER.info("Number of pages: " + numberOfPages);

		request.setAttribute(Attribute.BOOKS, books);
		request.setAttribute(Attribute.NUMBER_OF_PAGES, numberOfPages);
		request.setAttribute(Attribute.CURRENT_PAGE, page);
		return Page.ALL_BOOKS_VIEW;
	}
}
