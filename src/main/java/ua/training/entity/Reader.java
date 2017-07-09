package ua.training.entity;

public class Reader extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private String patronymic;
	private String phone;
	private String address;
	private String readerCardNumber;

	public Reader() {
	}

	public static class Builder extends User.Builder<Builder> {

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
		public Builder getThis() {
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
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((readerCardNumber == null) ? 0 : readerCardNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((!super.equals(obj)) || (getClass() != obj.getClass())) {
			return false;
		}

		Reader reader = (Reader) obj;

		if ((phone != null) ? !phone.equals(reader.phone) : reader.phone != null) {
			return false;
		}
		return ((readerCardNumber != null) ? readerCardNumber.equals(reader.readerCardNumber)
				: reader.readerCardNumber == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Reader [ [super: ").append(super.toString()).append("], name=").append(name)
				.append(", surname=").append(surname).append(", patronymic=").append(patronymic).append(", phone=").append(phone).append(", address=").append(address)
				.append(", readerCardNumber=").append(readerCardNumber).append("] ");
		return builder2.toString();
	}

}
