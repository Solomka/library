package ua.training.model.entity;

/**
 * Class that represents availability of the library book
 * 
 * @author Solomka
 *
 */
public enum Availability {
	READING_ROOM("reading room"), SUBSCRIPTION("subscription");

	private String value;

	Availability(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Provides Availability for a given String value
	 * 
	 * @param value
	 *            value describing Availability
	 * @return Availability or IllegalArgumentException if appropriate
	 *         availability wasn't found
	 */
	public static Availability forValue(String value) {
		for (final Availability availability : Availability.values()) {
			if (availability.getValue().equals(value)) {
				return availability;
			}
		}
		throw new RuntimeException("Avilability with such string value doesn't exist");
	}
}
