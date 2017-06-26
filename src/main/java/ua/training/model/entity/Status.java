package ua.training.model.entity;

/**
 * Class that represents availability status of the books' instances
 * 
 * @author Solomka
 *
 */
public enum Status {
	AVAILABLE("Available"), UNAVAILABLE("Unavailable");

	private String value;

	Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
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
