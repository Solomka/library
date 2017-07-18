package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.entity.BookInstance;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class BookInstanceValidator implements Validator<BookInstance> {

	private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private BookInstanceValidator() {
	}

	private static class Holder {
		static final BookInstanceValidator INSTANCE = new BookInstanceValidator();
	}

	public static BookInstanceValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(BookInstance bookInstance) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.INVENTORY_NUMBER, bookInstance.getInventoryNumber(), errors);

		return errors;
	}
}