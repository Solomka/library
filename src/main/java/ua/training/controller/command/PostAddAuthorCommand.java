package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.model.entity.Author;
import ua.training.model.service.AuthorService;
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
			RedirectionManager.redirectWithParamMessage(request, response, ServletPath.ALL_AUTHORS, Attribute.SUCCESS,
					Message.SUCCESS_AUTHOR_ADDITION);
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

	private void addRequestAtrributes(HttpServletRequest request, Author author, List<String> errors) {
		request.setAttribute(Attribute.AUTHOR, author);
		request.setAttribute(Attribute.ERRORS, errors);
	}

}
