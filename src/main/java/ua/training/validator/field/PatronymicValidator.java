package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class PatronymicValidator extends AbstractFieldValidatorHandler {

	private static final String PATRONYMIC_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$";
	
	private PatronymicValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final PatronymicValidator INSTANCE = new PatronymicValidator(FieldValidatorKey.PATRONYMIC);
	}

	public static PatronymicValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PATRONYMIC_REGEX)) {
			errors.add(Message.INVALID_PATRONYMIC_INPUT);
		}
	}
}