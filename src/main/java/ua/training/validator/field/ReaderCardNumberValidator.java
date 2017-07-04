package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class ReaderCardNumberValidator extends FieldValidator {

	private ReaderCardNumberValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);

	}

	private static final String READER_CARD_NUMBER_REGEX = "[a-zA-ZА-ЯІЇЄа-яіїє\\d]{10,13}";

	private static class Holder {
		static final ReaderCardNumberValidator INSTANCE = new ReaderCardNumberValidator(
				FieldValidatorKey.READER_CARD_NUMBER);
	}

	public static ReaderCardNumberValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(READER_CARD_NUMBER_REGEX)) {
			errors.add(Message.INVALID_READER_CARD_NUMBER);
		}

	}

}