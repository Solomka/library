package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.model.entity.Reader;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class ReaderValidator implements Validator<Reader> {

	private FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private ReaderValidator() {

	}

	private static class Holder {
		static final ReaderValidator INSTANCE = new ReaderValidator();
	}

	public static ReaderValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Reader reader) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.EMAIL, reader.getEmail(), errors);
		fieldValidator.validateField(FieldValidatorKey.PASSWORD, reader.getPassword(), errors);
		fieldValidator.validateField(FieldValidatorKey.NAME, reader.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.SURNAME, reader.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.PATRONYMIC, reader.getPatronymic(), errors);
		fieldValidator.validateField(FieldValidatorKey.PHONE, reader.getPhone(), errors);
		fieldValidator.validateField(FieldValidatorKey.ADDRESS, reader.getAddress(), errors);
		fieldValidator.validateField(FieldValidatorKey.READER_CARD_NUMBER, reader.getReaderCardNumber(), errors);

		return errors;
	}

}
