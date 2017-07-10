package ua.training.controller.command;

import java.io.IOException;
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
import ua.training.entity.Author;
import ua.training.locale.Message;
import ua.training.service.AuthorService;
import ua.training.validator.entity.AuthorValidator;

public class PostAddAuthorCommand implements Command {

	private AuthorService authorService;

	public PostAddAuthorCommand(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Author author = getUserInput(request);
		List<String> errors = validateUserInput(author);

		if (errors.isEmpty()) {
			authorService.createAuthor(author);
			redirectToAllAuthorsPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequestAtrributes(request, author, errors);
		return Page.ADD_AUTHOR_VIEW;
	}

	private Author getUserInput(HttpServletRequest request) {
		return new Author.Builder().setName(request.getParameter(Attribute.NAME))
				.setSurname(request.getParameter(Attribute.SURNAME)).setCountry(request.getParameter(Attribute.COUNTRY))
				.build();
	}

	private List<String> validateUserInput(Author author) {
		return AuthorValidator.getInstance().validate(author);
	}

	private void redirectToAllAuthorsPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_AUTHOR_ADDITION);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_AUTHORS, urlParams);
	}

	private void addRequestAtrributes(HttpServletRequest request, Author author, List<String> errors) {
		request.setAttribute(Attribute.AUTHOR, author);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
