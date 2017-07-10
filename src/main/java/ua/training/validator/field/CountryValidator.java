package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class CountryValidator extends FieldValidator {

	private static final String COUNTRY_REGEX = "^[a-zA-ZА-ЯІЇЄа-яіїє\\s’'-]{3,100}$";
	
	public CountryValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final CountryValidator INSTANCE = new CountryValidator(FieldValidatorKey.COUNTRY);
	}

	public static CountryValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(COUNTRY_REGEX)) {
			errors.add(Message.INVALID_COUNTRY_INPUT);
		}
	}
}