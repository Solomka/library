package ua.training.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String isbn;
	private String title;
	private String publisher;
	private LocalDate imprintDate;
	private Availability availability;

	private List<Author> authors = new ArrayList<>();
	private List<BookInstance> bookInstances = new ArrayList<>();

	public Book() {

	}

	public BookInstance addBookInstance(BookInstance instance) {
		bookInstances.add(instance);
		instance.setBook(this);
		return instance;
	}

	public Author addAuthor(Author author) {
		authors.add(author);
		return author;
	}

	public static class Builder implements IBuilder<Book> {

		private Book book = new Book();

		public Builder setId(Long id) {
			book.id = id;
			return this;
		}

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

		public Builder setImprintDate(LocalDate imprintDate) {
			book.imprintDate = imprintDate;
			return this;
		}

		public Builder setAvailability(Availability availability) {
			book.availability = availability;
			return this;
		}

		public Builder setBookInstances(List<BookInstance> bookInstances) {
			book.bookInstances = bookInstances;
			return this;
		}

		public Builder setAuthors(List<Author> authors) {
			book.authors = authors;
			return this;
		}

		@Override
		public Book build() {
			return book;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getImprintDate() {
		return imprintDate;
	}

	public void setImprintDate(LocalDate imprintDate) {
		this.imprintDate = imprintDate;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public List<BookInstance> getBookInstances() {
		return bookInstances;
	}

	public void setBookInstances(List<BookInstance> bookInstances) {
		this.bookInstances = bookInstances;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((bookInstances == null) ? 0 : bookInstances.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imprintDate == null) ? 0 : imprintDate.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (availability != other.availability)
			return false;
		if (bookInstances == null) {
			if (other.bookInstances != null)
				return false;
		} else if (!bookInstances.equals(other.bookInstances))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imprintDate == null) {
			if (other.imprintDate != null)
				return false;
		} else if (!imprintDate.equals(other.imprintDate))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Book [id=").append(id).append(", isbn=").append(isbn).append(", title=").append(title)
				.append(", publisher=").append(publisher).append(", imprintDate=").append(imprintDate)
				.append(", availability=").append(availability).append(", authors=").append(authors)
				.append(", bookInstances=").append(bookInstances).append("]");
		return builder2.toString();
	}

}
