package ua.training.entity;

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

	public static Status forValur(String value) {
		for (final Status status : Status.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException();
	}
}
