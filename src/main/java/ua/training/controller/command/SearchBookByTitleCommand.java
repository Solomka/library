package ua.training.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.locale.Message;
import ua.training.service.BookService;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class SearchBookByTitleCommand implements Command {

	private BookService bookService;

	public SearchBookByTitleCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter(Attribute.TITLE);
		List<String> errors = validateUserInput(title);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Book> books = bookService.searchBookByTitle(title);

		if (books.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.BOOK_IS_NOT_FOUND);
			RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.BOOKS, books);
		return Page.ALL_BOOKS_VIEW;

	}

	private List<String> validateUserInput(String title) {
		List<String> errors = new ArrayList<>();

		FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.TITLE, title, errors);
		return errors;
	}

}
