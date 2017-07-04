package ua.training.validator.field;

public final class FieldValidatorsChainGenerator {

	private FieldValidatorsChainGenerator() {

	}

	public static FieldValidator getFieldValidatorsChain() {
		FieldValidator emailFieldValidator = EmailValidator.getInstance();
		FieldValidator passwordFieldValidator = PasswordValidator.getInstance();

		emailFieldValidator.setNextFieldValidator(passwordFieldValidator);

		return emailFieldValidator;
	}

}
