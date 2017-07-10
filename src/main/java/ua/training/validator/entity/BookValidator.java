package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.entity.Book;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class BookValidator implements Validator<Book> {

	private FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private BookValidator() {
	}

	private static class Holder {
		static final BookValidator INSTANCE = new BookValidator();
	}

	public static BookValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Book book) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.ISBN, book.getIsbn(), errors);
		fieldValidator.validateField(FieldValidatorKey.TITLE, book.getTitle(), errors);
		fieldValidator.validateField(FieldValidatorKey.PUBLISHER, book.getPublisher(), errors);

		return errors;
	}
}
