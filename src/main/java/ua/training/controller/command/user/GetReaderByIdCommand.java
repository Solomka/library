package ua.training.controller.command.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Reader;
import ua.training.locale.Message;
import ua.training.service.UserService;

public class GetReaderByIdCommand implements Command {

	private UserService userService;

	public GetReaderByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long readerId = Long.valueOf(request.getParameter(Attribute.ID_READER));
		Optional<Reader> reader = userService.getReaderById(readerId);
		if (!reader.isPresent()) {
			redirectToAllOrdersPageWithErrorMessage(request, response);
		}
		request.setAttribute(Attribute.READERS, Arrays.asList(reader.get()));
		return Page.ALL_READERS_VIEW;
	}

	private void redirectToAllOrdersPageWithErrorMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.READER_IS_NOT_FOUND);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}
