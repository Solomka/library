package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class PasswordValidator extends FieldValidator {

	private static final String PASSWORD_REGEX = "^[\\wА-ЯІЇЄа-яіїє]{8,14}$";

	private PasswordValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}

	private static class Holder {
		static final PasswordValidator INSTANCE = new PasswordValidator(FieldValidatorKey.PASSWORD);
	}

	public static PasswordValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PASSWORD_REGEX)) {
			errors.add(Message.INVALID_PASS);
		}
	}
}