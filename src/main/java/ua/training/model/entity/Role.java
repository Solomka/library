package ua.training.model.entity;

/**
 * Class that represents users' roles
 * 
 * @author Solomka
 *
 */
public enum Role {
	LIBRARIAN("librarian"), READER("reader");

	private String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Provides Role for a given String value
	 * 
	 * @param value
	 *            value describing Role
	 * @return Role or RuntimeException if appropriate role wasn't found
	 */
	public static Role forValue(String value) {
		for (Role role : Role.values()) {
			if (role.getValue().equals(value)) {
				return role;
			}
		}
		throw new RuntimeException("Role with such string value doesn't exist");
	}
}
