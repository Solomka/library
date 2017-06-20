package ua.training.entity;

public enum Role {
	LIBRARIAN("Librarian"), STUDENT("Student");

	private String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public Role forValue(String value) {
		for (Role role : Role.values()) {
			if (role.getValue().equals(value)) {
				return role;
			}
		}
		throw new IllegalArgumentException();
	}
}
