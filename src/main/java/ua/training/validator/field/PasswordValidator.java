package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public final class PasswordValidator implements FieldValidator<String> {

	private static final String PASSWORD_REGEX = "^[\\d\\w]{8,14}$";

	private PasswordValidator() {

	}

	private static class Holder {
		static final PasswordValidator INSTANCE = new PasswordValidator();
	}

	public static PasswordValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PASSWORD_REGEX)) {
			errors.add(Message.WRONG_PASS);
		}

	}

}
