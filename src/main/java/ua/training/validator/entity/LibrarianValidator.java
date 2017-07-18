package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.entity.Librarian;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class LibrarianValidator implements Validator<Librarian> {

	private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

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

		fieldValidator.validateField(FieldValidatorKey.NAME, librarian.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.SURNAME, librarian.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.PATRONYMIC, librarian.getPatronymic(), errors);

		return errors;
	}
}