package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.model.entity.Librarian;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class LibrarianValidator implements Validator<Librarian> {

	private FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private LibrarianValidator() {

	}

	private static class Holder {
		static final LibrarianValidator INSTANCE = new LibrarianValidator();
	}

	public static LibrarianValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Librarian librarian) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, librarian.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, librarian.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.PLAIN_TEXT, librarian.getPatronymic(), errors);

		return errors;
	}

}