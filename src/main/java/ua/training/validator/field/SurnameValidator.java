package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class SurnameValidator extends FieldValidator {

	private static final String SURNAME_REGEX = "^[a-zA-ZА-ЯІЇЄа-яіїє\\s’'-]{3,100}$";
	
	public SurnameValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final SurnameValidator INSTANCE = new SurnameValidator(FieldValidatorKey.SURNAME);
	}

	public static SurnameValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(SURNAME_REGEX)) {
			errors.add(Message.INVALID_SURNAME_INPUT);
		}
	}
}