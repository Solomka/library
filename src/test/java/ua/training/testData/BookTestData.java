package ua.training.testData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ua.training.dto.BookDto;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.Status;

public final class BookTestData {

	private BookTestData() {

	}

	public static List<Book> generateBooksListWithAuthors() {
		return Arrays.asList(new Book[] {
				new Book.Builder().setId(new Long(1)).setIsbn("1111111111111").setTitle("Test Title")
						.setPublisher("Test Publisher1").setAuthors(generateAuthorsList())
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setId(new Long(2)).setIsbn("2222222222222").setTitle("Test Title")
						.setPublisher("Test Publisher2").setAuthors(generateAuthorsList())
						.setAvailability(Availability.SUBSCRIPTION).build() });
	}

	public static Optional<Book> generateBookOptionalWithAuthorsAndInstances() {
		return Optional.of(new Book.Builder().setId(new Long(1)).setIsbn("1111111111111").setTitle("Test Title1")
				.setPublisher("Test Publisher1").setAvailability(Availability.SUBSCRIPTION)
				.setAuthors(generateAuthorsList()).setBookInstances(generateAvailableBookInstancesList()).build());
	}

	public static Optional<Book> generateBookOptionalWithAuthors() {
		return Optional.of(new Book.Builder().setId(new Long(1)).setIsbn("1111111111111").setTitle("Test Title1")
				.setPublisher("Test Publisher1").setAvailability(Availability.SUBSCRIPTION)
				.setAuthors(generateAuthorsList()).build());
	}

	public static List<BookInstance> generateAvailableBookInstancesList() {
		return Arrays.asList(
				new BookInstance.Builder().setId(new Long(1)).setInventoryNumber("1234567898765")
						.setStatus(Status.AVAILABLE).setBook(new Book.Builder().setId(new Long(1)).build()).build(),
				new BookInstance.Builder().setId(new Long(2)).setInventoryNumber("8767586987465")
						.setStatus(Status.AVAILABLE).setBook(new Book.Builder().setId(new Long(2)).build()).build());
	}

	public static List<Book> generateBooksListWithSameAuthor() {
		return Arrays.asList(new Book[] {
				new Book.Builder().setId(new Long(1)).setIsbn("1111111111111").setTitle("Test Title")
						.setPublisher("Test Publisher1").setAuthors(generateAuthorAsList())
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setId(new Long(2)).setIsbn("2222222222222").setTitle("Test Title")
						.setPublisher("Test Publisher2").setAuthors(generateAuthorAsList())
						.setAvailability(Availability.SUBSCRIPTION).build() });
	}

	public static List<Author> generateAuthorAsList() {
		return Arrays.asList(new Author[] { new Author.Builder().setId(new Long(1)).setName("Daniel")
				.setSurname("Keyes").setCountry("America").build() });
	}

	public static BookDto generateBookDtoWithAuthors() {
		return new BookDto.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
				.setAvailability(Availability.SUBSCRIPTION).setAuthorsIds(new String[] { "1", "2" }).build();
	}

	public static Book generateBookForCreation() {
		return new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
				.setAvailability(Availability.SUBSCRIPTION).setAuthors(generateAuthorsList()).build();
	}

	public static List<Author> generateAuthorsList() {
		return Arrays.asList(new Author[] { new Author.Builder().setId(new Long(1)).build(),
				new Author.Builder().setId(new Long(2)).build() });
	}

}
