package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.dto.BookDto;
import ua.training.locale.Message;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class BookValidator implements Validator<BookDto> {

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
	public List<String> validate(BookDto book) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.ISBN, book.getIsbn(), errors);
		fieldValidator.validateField(FieldValidatorKey.TITLE, book.getTitle(), errors);
		fieldValidator.validateField(FieldValidatorKey.PUBLISHER, book.getPublisher(), errors);
		checkBookAuthors(book.getAuthorsIds(), errors);

		return errors;
	}

	private void checkBookAuthors(String[] authorsIds, List<String> errors) {
		if (authorsIds == null) {
			errors.add(Message.INVALID_BOOK_AUTHORS_SELECTION);
		}

	}
}
