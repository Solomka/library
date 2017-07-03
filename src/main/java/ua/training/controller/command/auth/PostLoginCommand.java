package ua.training.controller.command.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.training.controller.command.Command;
import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.dto.CredentialsDto;
import ua.training.controller.session.SessionManager;
import ua.training.locale.Message;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;
import ua.training.validator.dto.CredentialsValidator;

public class PostLoginCommand implements Command {

	private UserService userService;

	private CredentialsDto credentialsDto;
	private List<String> errors = new ArrayList<>();

	public PostLoginCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (SessionManager.isUserLoggedIn(session)) {
			return ServletPath.HOME;
		}

		validateUserInput(request);

		if (errors.isEmpty()) {
			if (userService.isUserWithCredentials(credentialsDto)) {
				getUserFromDB(session);
				return ServletPath.ALL_BOOKS;
			} else {
				errors.add(Message.WRONG_CREDENTIALS);
			}
		}

		addRequestAtrributes(request);
		return Page.LOGIN_VIEW;
	}

	private void validateUserInput(HttpServletRequest request) {
		credentialsDto = new CredentialsDto(request.getParameter(Attribute.EMAIL),
				request.getParameter(Attribute.PASSWORD));

		errors = CredentialsValidator.getInstance().validate(credentialsDto);
	}

	private void getUserFromDB(HttpSession session) {
		User user = userService.getUserByEmail(credentialsDto.getEmail()).get();
		//User user = userService.getUserByLogin(credentialsDto.getEmail()).get();
		SessionManager.addUserToSession(session, user);
	}

	private void addRequestAtrributes(HttpServletRequest request) {
		request.setAttribute(Attribute.LOGIN_USER, credentialsDto);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
