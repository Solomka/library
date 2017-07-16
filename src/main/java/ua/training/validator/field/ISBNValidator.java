package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class ISBNValidator extends FieldValidator {

	private static final String ISBN_REGEX = "^\\d{13}$";
	
	private ISBNValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final ISBNValidator INSTANCE = new ISBNValidator(FieldValidatorKey.ISBN);
	}

	public static ISBNValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(ISBN_REGEX)) {
			errors.add(Message.INVALID_ISBN);
		}
	}
}