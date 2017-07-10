package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class PublisherValidator extends FieldValidator {

	private static final String PUBLISHER_REGEX = "^[a-zA-ZА-ЯІЇЄа-яіїє\\s\\d’'-]{3,100}$";
	
	public PublisherValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final PublisherValidator INSTANCE = new PublisherValidator(FieldValidatorKey.PUBLISHER);
	}

	public static PublisherValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PUBLISHER_REGEX)) {
			errors.add(Message.INVALID_PUBLISHER_INPUT);
		}
	}
}