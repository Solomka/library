package ua.training.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.entity.BookOrder;
import ua.training.locale.Message;
import ua.training.service.BookOrderService;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class SearchOrderByReaderCardNumberCommand implements Command {
	private BookOrderService bookOrderService;

	public SearchOrderByReaderCardNumberCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String readerCardNumber = request.getParameter(Attribute.READER_CARD_NUMBER);
		
		List<String> errors = validateUserInput(readerCardNumber);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<BookOrder> orders = bookOrderService.searchNotReturnedOrdersByReaderCardNumber(readerCardNumber);

		if (orders.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.ORDERS_ARE_NOT_FOUND);
			RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;		
	}
	
	private List<String> validateUserInput(String readerCardNumber) {
		List<String> errors = new ArrayList<>();

		FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.READER_CARD_NUMBER, readerCardNumber, errors);
		return errors;
	}
}
