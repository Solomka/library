package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class TitleValidator extends AbstractFieldValidatorHandler {

	private static final String TITLE_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє\\d]+[\\wА-ЯІЇЄа-яіїє\\s’'-]{2,99}$";

	private TitleValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}

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