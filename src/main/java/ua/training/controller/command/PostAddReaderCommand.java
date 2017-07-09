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
import ua.training.locale.Message;
import ua.training.model.entity.Reader;
import ua.training.model.entity.Role;
import ua.training.model.service.UserService;
import ua.training.validator.entity.ReaderValidator;

public class PostAddReaderCommand implements Command {

	private UserService userService;

	public PostAddReaderCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Reader reader = getUserInput(request);
		List<String> errors = validateUserInput(reader);

		if (errors.isEmpty()) {
			userService.createReader(reader);
			redirectToAllReadersPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequestAtrributes(request, reader, errors);
		return Page.ADD_READER_VIEW;
	}

	private Reader getUserInput(HttpServletRequest request) {
		return new Reader.Builder().setEmail(request.getParameter(Attribute.EMAIL))
				.setPassword(request.getParameter(Attribute.READER_CARD_NUMBER)).setRole(Role.READER)
				.setReaderCardNumber(request.getParameter(Attribute.READER_CARD_NUMBER))
				.setSurname(request.getParameter(Attribute.SURNAME)).setName(request.getParameter(Attribute.NAME))
				.setPatronymic(request.getParameter(Attribute.PATRONYMIC))
				.setPhone(request.getParameter(Attribute.PHONE)).setAddress(request.getParameter(Attribute.ADDRESS))
				.build();
	}

	private List<String> validateUserInput(Reader reader) {
		return ReaderValidator.getInstance().validate(reader);
	}

	private void redirectToAllReadersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_READER_ADDITION);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_READERS, urlParams);
	}

	private void addRequestAtrributes(HttpServletRequest request, Reader reader, List<String> errors) {
		request.setAttribute(Attribute.READER, reader);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
