package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class PlainTextValidator extends FieldValidator {

	private PlainTextValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);

	}

	private static final String PLAIN_TEXT_REGEX = "^[a-zA-ZА-ЯІЇЄа-яіїє\\s’'-]{3,100}$";

	private static class Holder {
		static final PlainTextValidator INSTANCE = new PlainTextValidator(FieldValidatorKey.PLAIN_TEXT);
	}

	public static PlainTextValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PLAIN_TEXT_REGEX)) {
			errors.add(Message.INVALID_TEXT_INPUT);
		}

	}
}
