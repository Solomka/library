package ua.training.validator.field;

import java.util.List;

public abstract class FieldValidator {

	private FieldValidatorKey fieldValidatorKey;
	private FieldValidator nextFieldValidator;

	public FieldValidator(FieldValidatorKey fieldValidatorKey) {
		this.fieldValidatorKey = fieldValidatorKey;
	}

	public void setNextFieldValidator(FieldValidator nextFieldValidator) {
		this.nextFieldValidator = nextFieldValidator;
	}

	public void validateField(FieldValidatorKey fieldValidatorKey, String fieldValue, List<String> errors) {
		if (fieldValidatorKey.equals(this.fieldValidatorKey)) {
			validateField(fieldValue, errors);
		}
		if (nextFieldValidator != null) {
			nextFieldValidator.validateField(fieldValidatorKey, fieldValue, errors);
		}

	}

	abstract void validateField(String fieldValue, List<String> errors);

}
