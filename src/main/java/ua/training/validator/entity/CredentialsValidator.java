package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.controller.dto.CredentialsDto;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public final class CredentialsValidator implements Validator<CredentialsDto> {
	
	private FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private CredentialsValidator() {

	}

	private static class Holder {
		static final CredentialsValidator INSTANCE = new CredentialsValidator();
	}

	public static CredentialsValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(CredentialsDto dto) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.EMAIL, dto.getEmail(), errors);
		fieldValidator.validateField(FieldValidatorKey.PASSWORD, dto.getPassword(), errors);

		return errors;
	}

}
