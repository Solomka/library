package ua.training.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.locale.Message;
import ua.training.service.AuthorService;
import ua.training.service.BookService;
import ua.training.validator.entity.BookValidator;

public class PostAddBookCommand implements Command {

	private BookService bookService;
	private AuthorService authorService;

	public PostAddBookCommand(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Book book = getUserInput(request);
		List<String> errors = validateUserInput(book);
		checkBookAuthorsSelection(request, book, errors);

		if (errors.isEmpty()) {
			bookService.createBook(book);
			redirectToAllBooksPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequestAtrributes(request, book, errors);
		return Page.ADD_BOOK_VIEW;
	}

	private Book getUserInput(HttpServletRequest request) {
		return new Book.Builder().setIsbn(request.getParameter(Attribute.ISBN))
				.setTitle(request.getParameter(Attribute.TITLE)).setPublisher(request.getParameter(Attribute.PUBLISHER))
				.setAvailability(Availability.forValue(request.getParameter(Attribute.AVAILABILITY))).build();
	}

	private List<String> validateUserInput(Book book) {
		return BookValidator.getInstance().validate(book);
	}

	private void checkBookAuthorsSelection(HttpServletRequest request, Book book, List<String> errors) {
		String[] authorsIds = request.getParameterValues(Attribute.AUTHORS);
		if (authorsIds == null) {
			errors.add(Message.INVALID_BOOK_AUTHORS_SELECTION);
		} else {
			book.setAuthors(getBookAuthors(authorsIds));
		}
	}

	private List<Author> getBookAuthors(String[] authorsIds) {
		List<Author> bookAuthors = new ArrayList<>();

		for (String authorId : authorsIds) {
			bookAuthors.add(new Author.Builder().setId(new Long(authorId)).build());
		}
		return bookAuthors;
	}

	private void redirectToAllBooksPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_BOOK_ADDITION);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
	}

	private void addRequestAtrributes(HttpServletRequest request, Book book, List<String> errors) {
		List<Author> authors = authorService.getAllAuthors();
		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());
		request.setAttribute(Attribute.AUTHORS, authors);
		request.setAttribute(Attribute.BOOK, book);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
