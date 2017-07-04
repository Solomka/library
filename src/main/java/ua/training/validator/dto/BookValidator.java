package ua.training.validator.dto;

import java.util.ArrayList;
import java.util.List;

import ua.training.model.entity.Book;
import ua.training.validator.field.TitleValidator;

public class BookValidator implements Validator<Book> {

	private BookValidator() {

	}

	private static class Holder {
		static final BookValidator INSTANCE = new BookValidator();
	}

	public static BookValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Book dto) {
		List<String> errors = new ArrayList<>();

		//TitleValidator.getInstance().validateField(dto.getTitle(), errors);

		return errors;
	}

}
