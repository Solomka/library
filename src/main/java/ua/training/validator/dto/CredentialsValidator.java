package ua.training.validator.dto;

import java.util.ArrayList;
import java.util.List;

import ua.training.controller.dto.CredentialsDto;
import ua.training.validator.field.EmailValidator;
import ua.training.validator.field.PasswordValidator;

public final class CredentialsValidator implements Validator<CredentialsDto> {

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

		EmailValidator.getInstance().validateField(dto.getEmail(), errors);
		PasswordValidator.getInstance().validateField(dto.getPassword(), errors);

		return errors;
	}

}
