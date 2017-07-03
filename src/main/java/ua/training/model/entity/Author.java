package ua.training.model.entity;

import java.io.Serializable;

public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String surname;
	private String country;

	public Author() {

	}

	public static class Builder implements IBuilder<Author> {
		Author author = new Author();

		public Builder setId(Long id) {
			author.id = id;
			return this;
		}

		public Builder setName(String name) {
			author.name = name;
			return this;
		}

		public Builder setSurname(String surname) {
			author.surname = surname;
			return this;
		}

		public Builder setCountry(String country) {
			author.country = country;
			return this;
		}

		@Override
		public Author build() {
			return author;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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

		Author author = (Author) obj;

		if ((country != null) ? !country.equals(author.country) : author.country != null) {
			return false;
		}
		if ((name != null) ? !name.equals(author.name) : author.name != null) {
			return false;
		}
		
		return ((surname != null) ? surname.equals(author.surname) : author.surname == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Author [id=").append(id).append(", name=").append(name).append(", surname=").append(surname)
				.append(", country=").append(country).append("]");
		return builder2.toString();
	}

}
