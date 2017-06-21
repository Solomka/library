package ua.training.entity;

public class Reader extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private String patronymic;
	private String email;
	private String phone;
	private String address;
	private String readerCardNumber;

	public Reader(/*Long id, String login, String password, Role role*/) {
		//super(id, login, password, role);
	}

	public static class Builder extends User.Builder<Builder>  {

		private Reader reader;

		public Builder() {
			user = reader = new Reader();
		}

		public Builder setName(String name) {
			reader.name = name;
			return this;
		}

		public Builder setSurname(String surname) {
			reader.surname = surname;
			return this;
		}

		public Builder setPatronymic(String patronymic) {
			reader.patronymic = patronymic;
			return this;
		}

		public Builder setEmail(String email) {
			reader.email = email;
			return this;
		}

		public Builder setPhone(String phone) {
			reader.phone = phone;
			return this;
		}

		public Builder setAddress(String address) {
			reader.address = address;
			return this;
		}

		public Builder setReaderCardNumber(String readerCardNumber) {
			reader.readerCardNumber = readerCardNumber;
			return this;
		}

		@Override
		public Reader build() {
			return reader;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReaderCardNumber() {
		return readerCardNumber;
	}

	public void setReaderCardNumber(String readerCardNumber) {
		this.readerCardNumber = readerCardNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((patronymic == null) ? 0 : patronymic.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((readerCardNumber == null) ? 0 : readerCardNumber.hashCode());
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
		Reader other = (Reader) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
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
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (readerCardNumber == null) {
			if (other.readerCardNumber != null)
				return false;
		} else if (!readerCardNumber.equals(other.readerCardNumber))
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
		builder2.append("Reader [name=").append(name).append(", surname=").append(surname).append(", patronymic=")
				.append(patronymic).append(", email=").append(email).append(", phone=").append(phone)
				.append(", address=").append(address).append(", readerCardNumber=").append(readerCardNumber)
				.append("]");
		return builder2.toString();
	}

}
