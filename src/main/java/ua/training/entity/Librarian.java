package ua.training.entity;

public class Librarian extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private String patronymic;

	public Librarian() {
	}

	public static class Builder extends User.Builder<Builder> {

		private Librarian librarian;

		public Builder() {
			user = librarian = new Librarian();
		}

		public Builder setName(String name) {
			librarian.name = name;
			return this;
		}

		public Builder setSurname(String surname) {
			librarian.surname = surname;
			return this;
		}

		public Builder setPatronymic(String patronymic) {
			librarian.patronymic = patronymic;
			return this;
		}

		@Override
		public Builder getThis() {
			return this;
		}

		@Override
		public Librarian build() {
			return librarian;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((patronymic == null) ? 0 : patronymic.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}

		Librarian librarian = (Librarian) obj;

		if ((name != null) ? !name.equals(librarian.name) : librarian.name != null) {
			return false;
		}
		if ((patronymic != null) ? !patronymic.equals(librarian.patronymic) : librarian.patronymic != null) {
			return false;
		}
		return ((surname != null) ? surname.equals(librarian.surname) : librarian.surname == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Librarian [ [super: ").append(super.toString()).append("], name=").append(name)
				.append(", surname=").append(surname).append(", patronymic=").append(patronymic).append("] ");
		return builder2.toString();
	}

}
