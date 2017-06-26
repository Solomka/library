package ua.training.model.entity;

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
			book.bookInstances = new ArrayList<>();
			for (BookInstance bookInstance : bookInstances) {
				book.bookInstances.add(bookInstance);
			}

			return this;
		}

		public Builder setAuthors(List<Author> authors) {
			book.authors = new ArrayList<>();
			for (Author author : authors) {
				book.authors.add(author);
			}
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
		this.bookInstances = new ArrayList<>();
		for (BookInstance bookInstance : bookInstances) {
			this.bookInstances.add(bookInstance);
		}
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = new ArrayList<>();
		for (Author author : authors) {
			this.authors.add(author);
		}
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

		Book book = (Book) obj;

		return ((isbn != null) ? isbn.equals(book.isbn) : book.isbn == null);
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
