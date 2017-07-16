package ua.training.validator.field;

import java.util.List;

/**
 * abstract class that is basic class for all entity/dto fields validators
 * 
 * @author Solomka
 *
 */
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