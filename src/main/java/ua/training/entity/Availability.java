package ua.training.entity;

/**
 * Class that represents availability of the library book
 * 
 * @author Solomka
 *
 */
public enum Availability {
	READING_ROOM("Reading room"), SUBSCRIPTION("Subscription");

	private String value;

	Availability(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Availability forValue(String value) {
		for (final Availability availability : Availability.values()) {
			if (availability.getValue().equals(value)) {
				return availability;
			}
		}

		throw new IllegalArgumentException();
	}

}
