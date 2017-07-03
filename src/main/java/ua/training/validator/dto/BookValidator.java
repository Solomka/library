package ua.training.validator.dto;

import java.util.ArrayList;
import java.util.List;

import ua.training.controller.dto.BookDto;
import ua.training.validator.field.TitleValidator;

public class BookValidator implements Validator<BookDto> {

	private BookValidator() {

	}

	private static class Holder {
		static final BookValidator INSTANCE = new BookValidator();
	}

	public static BookValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(BookDto dto) {
		List<String> errors = new ArrayList<>();

		TitleValidator.getInstance().validateField(dto.getTitle(), errors);

		return errors;
	}

}
