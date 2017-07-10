package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.entity.Author;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class AuthorValidator implements Validator<Author> {

	private FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private AuthorValidator() {
	}

	private static class Holder {
		static final AuthorValidator INSTANCE = new AuthorValidator();
	}

	public static AuthorValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Author author) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.NAME, author.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.SURNAME, author.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.COUNTRY, author.getCountry(), errors);

		return errors;
	}
}
