package ua.training.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.service.impl.BookService;

public class BookServiceTest {

	@Ignore
	@Test
	public void testCreateBook() {
		BookService service = BookService.getInstance();

		Book book = new Book.Builder().setIsbn("4567123454678").setTitle("Atlas Shrugged").setPublisher("American news")
				.setImprintDate(LocalDate.parse("04/04/1971", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.setAvailability(Availability.SUBSCRIPTION).build();
		service.createBook(book);
		System.out.println("Book id: " + book.getId());

	}

}
