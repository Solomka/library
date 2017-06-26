package ua.training.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BookOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private BookInstance bookInstance;
	private Reader reader;
	private LocalDateTime creationDate;
	private LocalDateTime fulfilmentDate;
	private LocalDateTime pickUpDate;
	private LocalDateTime returnDate;
	private Librarian librarian;

	public BookOrder() {

	}

	public static class Builder implements IBuilder<BookOrder> {
		private BookOrder bookOrder = new BookOrder();

		public Builder setId(Long id) {
			bookOrder.id = id;
			return this;
		}

		public Builder setBookInstance(BookInstance bookInstance) {
			bookOrder.bookInstance = bookInstance;
			return this;
		}

		public Builder setReader(Reader reader) {
			bookOrder.reader = reader;
			return this;
		}

		public Builder setCreationDate(LocalDateTime creationDate) {
			bookOrder.creationDate = creationDate;
			return this;
		}

		public Builder setFulfilmentDate(LocalDateTime fulfilmentDate) {
			bookOrder.fulfilmentDate = fulfilmentDate;
			return this;
		}

		public Builder setPickUpDate(LocalDateTime pickUpDate) {
			bookOrder.pickUpDate = pickUpDate;
			return this;
		}

		public Builder setReturnDate(LocalDateTime returnDate) {
			bookOrder.returnDate = returnDate;
			return this;
		}

		public Builder setLibrarian(Librarian librarian) {
			bookOrder.librarian = librarian;
			return this;
		}

		@Override
		public BookOrder build() {
			return bookOrder;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookInstance getBookInstance() {
		return bookInstance;
	}

	public void setBookInstance(BookInstance bookInstance) {
		this.bookInstance = bookInstance;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getFulfilmentDate() {
		return fulfilmentDate;
	}

	public void setFulfilmentDate(LocalDateTime fulfilmentDate) {
		this.fulfilmentDate = fulfilmentDate;
	}

	public LocalDateTime getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(LocalDateTime pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookInstance == null) ? 0 : bookInstance.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((reader == null) ? 0 : reader.hashCode());
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

		BookOrder bookOrder = (BookOrder) obj;

		if ((bookInstance != null) ? !bookInstance.equals(bookOrder.bookInstance) : bookOrder.bookInstance != null) {
			return false;
		}
		if ((creationDate != null) ? !creationDate.equals(bookOrder.creationDate) : bookOrder.creationDate != null) {
			return false;
		}
		return ((reader != null) ? reader.equals(bookOrder.reader) : bookOrder.reader == null);

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookOrder [id=").append(id).append(", bookInstance=").append(bookInstance).append(", reader=")
				.append(reader).append(", creationDate=").append(creationDate).append(", fulfilmentDate=")
				.append(fulfilmentDate).append(", pickUpDate=").append(pickUpDate).append(", returnDate=")
				.append(returnDate).append(", librarian=").append(librarian).append("]");
		return builder.toString();
	}

}
