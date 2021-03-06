package ua.training.controller.command.bookInstance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.Status;
import ua.training.locale.Message;
import ua.training.service.BookInstanceService;
import ua.training.validator.entity.BookInstanceValidator;

public class PostAddBookInstanceCommand implements Command {

	private BookInstanceService bookInstancesService;

	public PostAddBookInstanceCommand(BookInstanceService bookInstancesService) {
		this.bookInstancesService = bookInstancesService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookInstance bookInstance = getUserInput(request);
		List<String> errors = validateUserInput(bookInstance);

		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = generateUrlParams(bookInstance.getBook().getId().toString());

		if (errors.isEmpty()) {
			bookInstancesService.createBookInstance(bookInstance);
			urlParams.put(Attribute.SUCCESS, Message.SUCCESS_BOOK_INSTANCE_ADDITION);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.BOOK_INSTANCES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		urlParams.put(Attribute.ERROR, errors.get(0));
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.BOOK_INSTANCES, urlParams);
		return RedirectionManager.REDIRECTION;
	}

	private BookInstance getUserInput(HttpServletRequest request) {

		return new BookInstance.Builder().setInventoryNumber(request.getParameter(Attribute.INVENTORY_NUMBER))
				.setStatus(Status.AVAILABLE)
				.setBook(new Book.Builder().setId(Long.parseLong(request.getParameter(Attribute.ID_BOOK))).build())
				.build();
	}

	private List<String> validateUserInput(BookInstance bookInstance) {
		return BookInstanceValidator.getInstance().validate(bookInstance);
	}

	public Map<String, String> generateUrlParams(String bookId) {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ID_BOOK, bookId);
		return urlParams;
	}
}
