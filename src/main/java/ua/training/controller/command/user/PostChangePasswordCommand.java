package ua.training.controller.command.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.dto.ChangePasswordDto;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.validator.entity.ChangePasswordDtoValidator;

public class PostChangePasswordCommand implements Command {

	private UserService userService;

	public PostChangePasswordCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ChangePasswordDto changePasswordDto = getUserInput(request);
		List<String> errors = validateUserInput(changePasswordDto);

		if (!errors.isEmpty()) {
			addRequestAttributes(request, errors);
			return Page.CHANGE_PASSWORD_VIEW;
		}
		if (userService.changePassword(changePasswordDto)) {
			SessionManager.getInstance().addUserToSession(request.getSession(), userService.getReaderById(changePasswordDto.getUserId()).get());
			redirectToHomePageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		errors.add(Message.INVALID_OLD_PASSWORD);
		addRequestAttributes(request, errors);
		return Page.CHANGE_PASSWORD_VIEW;
	}

	private void redirectToHomePageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_PASSWORD_CHANGE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.HOME, urlParams);
	}

	private ChangePasswordDto getUserInput(HttpServletRequest request) {
		return new ChangePasswordDto.Builder().setUserId(SessionManager.getInstance().getUserFromSession(request.getSession()).getId())
				.setOldPassword(request.getParameter(Attribute.OLD_PASSWORD))
				.setNewPassword(request.getParameter(Attribute.NEW_PASSWORD))
				.setConfirmPassword(request.getParameter(Attribute.CONFIRM_NEW_PASSWORD)).build();
	}

	private List<String> validateUserInput(ChangePasswordDto changePasswordDto) {
		return ChangePasswordDtoValidator.getInstance().validate(changePasswordDto);
	}

	private void addRequestAttributes(HttpServletRequest request, List<String> errors) {
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
