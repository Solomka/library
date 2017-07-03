package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class TitleValidator implements FieldValidator<String> {

	private static final String NAME_REGEX = "^([A-Z][a-z]+)$";

	private TitleValidator() {

	}

	private static class Holder {
		static final TitleValidator INSTANCE = new TitleValidator();
	}

	public static TitleValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		errors.add(Message.WRONG_TITLE);

	}

}
