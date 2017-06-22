package ua.training.entity;

import java.io.Serializable;

public class BookInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String inventoryNumber;
	private Status status;
	private int shelfNumber;
	private int rowNumber;

	private Book book;

	public BookInstance() {

	}

	public static class Builder implements IBuilder<BookInstance> {
		BookInstance bookInstance = new BookInstance();

		public Builder setId(Long id) {
			bookInstance.id = id;
			return this;
		}

		public Builder setInventoryNumber(String inventoryNumber) {
			bookInstance.inventoryNumber = inventoryNumber;
			return this;
		}

		public Builder setStatus(Status status) {
			bookInstance.status = status;
			return this;
		}

		public Builder setShelfNumber(int shelfNumber) {
			bookInstance.shelfNumber = shelfNumber;
			return this;
		}

		public Builder setRowNumber(int rowNumber) {
			bookInstance.rowNumber = rowNumber;
			return this;
		}

		public Builder setBook(Book book) {
			bookInstance.book = book;
			return this;
		}

		@Override
		public BookInstance build() {
			return bookInstance;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getShelfNumber() {
		return shelfNumber;
	}

	public void setShelfNumber(int shelfNumber) {
		this.shelfNumber = shelfNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inventoryNumber == null) ? 0 : inventoryNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		BookInstance bookInstance = (BookInstance) obj;
		
		if(id != null ? !id.equals(bookInstance.id) : bookInstance.id != null) return false;
		return inventoryNumber != null ? inventoryNumber.equals(bookInstance.inventoryNumber) : bookInstance.inventoryNumber == null;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("BookInstance [id=").append(id).append(", inventoryNumber=").append(inventoryNumber)
				.append(", status=").append(status).append(", shelfNumber=").append(shelfNumber).append(", rowNumber=")
				.append(rowNumber).append(", book=").append(book).append("]");
		return builder2.toString();
	}

}
