package ua.training.validator.field;

import ua.training.model.entity.Status;

public enum FieldValidatorKey {

	NAME("name"), SURNAME("surname"), PATRONYMIC("patronumic"), PUBLISHER("publisher"), COUNTRY("country"), EMAIL(
			"email"), PASSWORD("password"), ISBN("isbn"), TITLE("title"), INVENTORY_NUMBER(
					"inventory number"), PHONE("phone"), ADDRESS("address"), READER_CARD_NUMBER("reader card number");

	private String value;

	FieldValidatorKey(String value) {

	}

	public String getValue() {
		return value;
	}

	public static Status forValur(String value) {
		for (final Status status : Status.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new RuntimeException("FieldValidatorKey with such string value doesn't exist");
	}

}
