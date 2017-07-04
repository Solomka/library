package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.model.entity.Author;
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

		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, author.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, author.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, author.getCountry(), errors);

		return errors;
	}
}
