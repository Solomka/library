package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.model.entity.Book;
import ua.training.model.entity.BookInstance;
import ua.training.model.entity.Status;
import ua.training.model.service.BookInstanceService;
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

		if (errors.isEmpty()) {
			bookInstancesService.addBookInstance(bookInstance);
			RedirectionManager.redirectWithParamMessageAndBookId(request, response, ServletPath.BOOK_INSTANCES,
					Attribute.SUCCESS, Message.SUCCESS_BOOK_INSTANCE_ADDITION,
					bookInstance.getBook().getId().toString());
			return RedirectionManager.REDIRECTION;
		}

		RedirectionManager.redirectWithParamMessageAndBookId(request, response, ServletPath.BOOK_INSTANCES,
				Attribute.ERROR, errors.get(0), bookInstance.getBook().getId().toString());
		return RedirectionManager.REDIRECTION;
	}

	private BookInstance getUserInput(HttpServletRequest request) {

		return new BookInstance.Builder().setInventoryNumber(request.getParameter(Attribute.INVENTORY_NUMBER))
				.setStatus(Status.AVAILABLE)
				.setBook(new Book.Builder().setId(Long.parseLong(request.getParameter(Attribute.ID_BOOK))).build()).build();
		
	}

	private List<String> validateUserInput(BookInstance bookInstance) {
		return BookInstanceValidator.getInstance().validate(bookInstance);
	}

}
