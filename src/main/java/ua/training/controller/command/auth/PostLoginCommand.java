package ua.training.controller.command.auth;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.dto.CredentialsDto;
import ua.training.controller.session.SessionManager;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.validator.entity.CredentialsDtoValidator;

public class PostLoginCommand implements Command {

	private UserService userService;

	public PostLoginCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (SessionManager.isUserLoggedIn(session)) {
			RedirectionManager.redirect(request, response, ServletPath.HOME);
			return RedirectionManager.REDIRECTION;
		}

		CredentialsDto credentialsDto = getUserInput(request);
		List<String> errors = validateUserInput(credentialsDto);

		if (!errors.isEmpty()) {
			addRequestAtrributes(request, credentialsDto, errors);
			return Page.LOGIN_VIEW;
		}

		Optional<User> user = userService.getUserByEmail(credentialsDto);
		if (user.isPresent()) {
			SessionManager.addUserToSession(session, user.get());
			RedirectionManager.redirect(request, response, ServletPath.HOME);
			return RedirectionManager.REDIRECTION;
		}
		errors.add(Message.INVALID_CREDENTIALS);

		addRequestAtrributes(request, credentialsDto, errors);
		return Page.LOGIN_VIEW;
	}

	private CredentialsDto getUserInput(HttpServletRequest request) {
		return new CredentialsDto(request.getParameter(Attribute.EMAIL), request.getParameter(Attribute.PASSWORD));
	}

	private List<String> validateUserInput(CredentialsDto credentialsDto) {
		return CredentialsDtoValidator.getInstance().validate(credentialsDto);
	}

	private void addRequestAtrributes(HttpServletRequest request, CredentialsDto credentialsDto, List<String> errors) {
		request.setAttribute(Attribute.LOGIN_USER, credentialsDto);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
