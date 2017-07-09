package ua.training.entity;

import ua.training.locale.Message;

/**
 * Class that represents availability status of the books' instances
 * 
 * @author Solomka
 *
 */
public enum Status {
	AVAILABLE("available", Message.STATUS_AVAILABLE), UNAVAILABLE("unavailable", Message.STATUS_UNAVAILABLE);

	private String value;
	private String localizedValue;

	Status(String value) {
		this.value = value;
	}

	Status(String value, String localizedValue) {
		this.value = value;
		this.localizedValue = localizedValue;
	}

	public String getValue() {
		return value;
	}

	public String getLocalizedValue() {
		return localizedValue;
	}

	/**
	 * Provides Status for a given String value
	 * 
	 * @param value
	 *            value describing Status
	 * @return Status or RuntimeException if appropriate status wasn't found
	 */
	public static Status forValur(String value) {
		for (final Status status : Status.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new RuntimeException("Status with such string value doesn't exist");
	}
}
