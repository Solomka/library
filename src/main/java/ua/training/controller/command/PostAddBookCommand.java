package ua.training.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.model.entity.Availability;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;
import ua.training.validator.entity.BookValidator;

public class PostAddBookCommand implements Command {

	private BookService bookService;

	private List<String> errors = new ArrayList<>();
	private Book book;

	public PostAddBookCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		validateUserInput(request);

		if (errors.isEmpty()) {
			bookService.createBook(book);
			return ServletPath.ALL_BOOKS;
		}

		addRequestAtrributes(request);
		return Page.ADD_BOOK_VIEW;
	}

	private void validateUserInput(HttpServletRequest request) {

		book = new Book.Builder().setIsbn(request.getParameter(Attribute.ISBN))
				.setTitle(request.getParameter(Attribute.TITLE)).setPublisher(request.getParameter(Attribute.PUBLISHER))
				.setAvailability(Availability.forValue(request.getParameter(Attribute.AVAILABILITY))).build();

		errors = BookValidator.getInstance().validate(book);

	}

	private void addRequestAtrributes(HttpServletRequest request) {
		request.setAttribute(Attribute.BOOK, book);
		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());
		request.setAttribute(Attribute.ERRORS, errors);

	}

}
