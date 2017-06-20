package ua.training.entity;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookInstance == null) ? 0 : bookInstance.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((fulfilmentDate == null) ? 0 : fulfilmentDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pickUpDate == null) ? 0 : pickUpDate.hashCode());
		result = prime * result + ((reader == null) ? 0 : reader.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
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
		BookOrder other = (BookOrder) obj;
		if (bookInstance == null) {
			if (other.bookInstance != null)
				return false;
		} else if (!bookInstance.equals(other.bookInstance))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (fulfilmentDate == null) {
			if (other.fulfilmentDate != null)
				return false;
		} else if (!fulfilmentDate.equals(other.fulfilmentDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pickUpDate == null) {
			if (other.pickUpDate != null)
				return false;
		} else if (!pickUpDate.equals(other.pickUpDate))
			return false;
		if (reader == null) {
			if (other.reader != null)
				return false;
		} else if (!reader.equals(other.reader))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("BookOrder [id=").append(id).append(", bookInstance=").append(bookInstance).append(", reader=")
				.append(reader).append(", creationDate=").append(creationDate).append(", fulfilmentDate=")
				.append(fulfilmentDate).append(", pickUpDate=").append(pickUpDate).append(", returnDate=")
				.append(returnDate).append("]");
		return builder2.toString();
	}

}
