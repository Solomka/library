package ua.training.testData;

import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.Status;

public final class BookInstanceTestData {

	private BookInstanceTestData() {

	}

	public static BookInstance generateBookInstance() {
		return new BookInstance.Builder().setStatus(Status.AVAILABLE).setInventoryNumber("7689587645876")
				.setBook(new Book.Builder().setId(new Long(1)).build()).build();
	}

}
