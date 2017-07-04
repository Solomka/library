package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class TitleValidator extends FieldValidator {

	private TitleValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);

	}

	private static final String TITLE_REGEX = "[\\wА-ЯІЇЄа-яіїє\\d\\s’'-]{3,100}";

	private static class Holder {
		static final TitleValidator INSTANCE = new TitleValidator(FieldValidatorKey.TITLE);
	}

	public static TitleValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(TITLE_REGEX)) {
			errors.add(Message.INVALID_TITLE);
		}

	}

}
