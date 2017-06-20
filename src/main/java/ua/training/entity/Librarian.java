package ua.training.entity;

public class Librarian extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private String patronymic;
	private String email;

	public Librarian() {

	}

	public Librarian(Long id, String login, String password, Role role) {
		super(id, login, password, role);
	}

	public static class Builder implements IBuilder<Librarian> {

		private Librarian librarian;

		public Builder(Long id, String login, String password, Role role) {
			librarian = new Librarian(id, login, password, role);
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

		public Builder setEmail(String email) {
			librarian.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((patronymic == null) ? 0 : patronymic.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Librarian other = (Librarian) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (patronymic == null) {
			if (other.patronymic != null)
				return false;
		} else if (!patronymic.equals(other.patronymic))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Librarian [name=").append(name).append(", surname=").append(surname).append(", patronymic=")
				.append(patronymic).append(", email=").append(email).append("]");
		return builder2.toString();
	}

}
