package ua.training.model.entity;

import ua.training.locale.Message;

/**
 * Class that represents availability of the library book
 * 
 * @author Solomka
 *
 */
public enum Availability {
	READING_ROOM("reading room", Message.AVAILABILITY_READING_ROOM), SUBSCRIPTION("subscription", Message.AVAILABILITY_SUBSCRIPTION);

	private String value;
	private String localizedValue;

	Availability(String value) {
		this.value = value;
	}
	
	Availability(String value, String localizedValue) {		
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
