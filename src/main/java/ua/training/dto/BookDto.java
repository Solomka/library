package ua.training.dto;

import java.util.Arrays;

import ua.training.entity.Availability;
import ua.training.entity.IBuilder;

public class BookDto {

	private String isbn;
	private String title;
	private String publisher;
	private Availability availability;

	private String[] authorsIds;

	public BookDto() {
	}

	public static class Builder implements IBuilder<BookDto> {

		private BookDto book = new BookDto();

		public Builder setIsbn(String isbn) {
			book.isbn = isbn;
			return this;
		}

		public Builder setTitle(String title) {
			book.title = title;
			return this;
		}

		public Builder setPublisher(String publisher) {
			book.publisher = publisher;
			return this;
		}

		public Builder setAvailability(Availability availability) {
			book.availability = availability;
			return this;
		}

		public Builder setAuthorsIds(String[] authorsIds) {
			book.authorsIds = authorsIds;
			return this;
		}

		@Override
		public BookDto build() {
			return book;
		}

	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public String[] getAuthorsIds() {
		return authorsIds;
	}

	public void setAuthorsIds(String[] authorsIds) {
		this.authorsIds = authorsIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
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

		BookDto book = (BookDto) obj;

		return ((isbn != null) ? isbn.equals(book.isbn) : book.isbn == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("BookDto [ [super: ").append(super.toString()).append("], isbn=").append(isbn)
				.append(", title=").append(title).append(", publisher=").append(publisher).append(", availability=")
				.append(availability).append(", authorsIds=").append(Arrays.toString(authorsIds)).append("] ");
		return builder2.toString();
	}
}