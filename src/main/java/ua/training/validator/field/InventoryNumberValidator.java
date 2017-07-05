package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class InventoryNumberValidator extends FieldValidator {

	private InventoryNumberValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);

	}

	private static final String INVENTORY_NUMBER_REGEX = "^\\d{7,13}$";

	private static class Holder {
		static final InventoryNumberValidator INSTANCE = new InventoryNumberValidator(FieldValidatorKey.INVENTORY_NUMBER);
	}

	public static InventoryNumberValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(INVENTORY_NUMBER_REGEX)) {
			errors.add(Message.INVALID_INVENTORY_NUMBER);
		}

	}

}
