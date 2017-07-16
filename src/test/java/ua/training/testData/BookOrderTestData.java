package ua.training.testData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.entity.Librarian;
import ua.training.entity.Reader;
import ua.training.entity.Status;

public final class BookOrderTestData {

	private BookOrderTestData() {

	}

	public static List<BookOrder> generateUnfulfilledBookOrdersList() {
		return Arrays.asList(new BookOrder[] {
				new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 15))
						.setBookInstance(new BookInstance.Builder().setId(new Long(1)).build())
						.setReader(new Reader.Builder().setId(new Long(2)).setReaderCardNumber("KB67890987567").build())
						.build(),
				new BookOrder.Builder().setId(new Long(2)).setCreationDate(LocalDate.of(2017, 06, 17))
						.setBookInstance(new BookInstance.Builder().setId(new Long(2)).build())
						.setReader(new Reader.Builder().setId(new Long(2)).setReaderCardNumber("KB67890987567").build())
						.build() });
	}

	public static List<BookOrder> generateOutstandingBookOrdersList() {
		return Arrays.asList(new BookOrder[] {
				new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 15))
						.setFulfilmentDate(LocalDate.of(2017, 06, 15)).setPickUpDate(LocalDate.of(2017, 06, 16))
						.setReturnDate(LocalDate.of(2017, 07, 16)).setActualReturnDate(null)
						.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
						.setReader(new Reader.Builder().setId(new Long(2)).build())
						.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build(),
				new BookOrder.Builder().setId(new Long(2)).setCreationDate(LocalDate.of(2017, 06, 17))
						.setFulfilmentDate(LocalDate.of(2017, 06, 17)).setPickUpDate(LocalDate.of(2017, 06, 18))
						.setReturnDate(LocalDate.of(2017, 07, 18)).setActualReturnDate(null)
						.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
						.setReader(new Reader.Builder().setId(new Long(2)).build())
						.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build() });
	}

	public static List<BookOrder> generateOrdersForReadingRoomReturnList() {
		return Arrays.asList(new BookOrder[] {
				new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 15))
						.setFulfilmentDate(LocalDate.of(2017, 06, 15)).setPickUpDate(LocalDate.of(2017, 06, 16))
						.setReturnDate(LocalDate.of(2017, 07, 16)).setActualReturnDate(null)
						.setBookInstance(generateReadingRoomUnavailableOrderBookInstance())
						.setReader(new Reader.Builder().setId(new Long(2)).build())
						.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build(),
				new BookOrder.Builder().setId(new Long(2)).setCreationDate(LocalDate.of(2017, 06, 17))
						.setFulfilmentDate(LocalDate.of(2017, 06, 17)).setPickUpDate(LocalDate.of(2017, 06, 18))
						.setReturnDate(LocalDate.of(2017, 07, 18)).setActualReturnDate(null)
						.setBookInstance(generateReadingRoomUnavailableOrderBookInstance())
						.setReader(new Reader.Builder().setId(new Long(2)).build())
						.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build() });
	}

	public static BookOrder generateOrderForCreation() {
		return new BookOrder.Builder().setCreationDate(LocalDate.of(2017, 06, 17))
				.setBookInstance(new BookInstance.Builder().setId(new Long(1)).build())
				.setReader(new Reader.Builder().setId(new Long(1)).build()).build();
	}

	public static BookOrder generateOrderWithCreationDate() {
		return new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 17))
				.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
				.setReader(new Reader.Builder().setId(new Long(1)).build()).build();
	}

	public static BookOrder generateOrderWithFulfilmentDate() {
		return new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 17))
				.setFulfilmentDate(LocalDate.of(2017, 06, 17))
				.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
				.setReader(new Reader.Builder().setId(new Long(1)).build())
				.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build();
	}

	public static BookOrder generateOrderWithFulfilmentPickUpReturnDateAndSubcriptionBook() {
		return new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 17))
				.setFulfilmentDate(LocalDate.of(2017, 06, 17)).setPickUpDate(LocalDate.of(2017, 06, 18))
				.setReturnDate(LocalDate.of(2017, 07, 18))
				.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
				.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build();
	}

	public static BookOrder generateOrderWithFulfilmentPickUpReturnActualReturnDateAndSubcriptionBook() {
		return new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 17))
				.setFulfilmentDate(LocalDate.of(2017, 06, 17)).setPickUpDate(LocalDate.of(2017, 06, 18))
				.setReturnDate(LocalDate.of(2017, 07, 18)).setActualReturnDate(LocalDate.of(2017, 07, 18))
				.setBookInstance(generateSubscriptionUnavailableOrderBookInstance())
				.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build();
	}

	public static BookOrder generateOrderWithFulfilmentPickUpReturnDateAndReadingRoomBook() {
		return new BookOrder.Builder().setId(new Long(1)).setCreationDate(LocalDate.of(2017, 06, 17))
				.setFulfilmentDate(LocalDate.of(2017, 06, 17)).setPickUpDate(LocalDate.of(2017, 06, 17))
				.setReturnDate(LocalDate.of(2017, 07, 17))
				.setBookInstance(generateReadingRoomUnavailableOrderBookInstance())
				.setReader(new Reader.Builder().setId(new Long(1)).build())
				.setLibrarian(new Librarian.Builder().setId(new Long(2)).build()).build();
	}

	public static BookInstance generateReadingRoomUnavailableOrderBookInstance() {
		return new BookInstance.Builder().setId(new Long(1)).setInventoryNumber("1234567898765")
				.setStatus(Status.UNAVAILABLE)
				.setBook(new Book.Builder().setId(new Long(1)).setAvailability(Availability.READING_ROOM).build())
				.build();
	}

	public static BookInstance generateSubscriptionUnavailableOrderBookInstance() {
		return new BookInstance.Builder().setId(new Long(1)).setInventoryNumber("1234567898765")
				.setStatus(Status.UNAVAILABLE)
				.setBook(new Book.Builder().setId(new Long(1)).setAvailability(Availability.SUBSCRIPTION).build())
				.build();
	}

	public static BookInstance generateReadingRoomAvailableOrderBookInstance() {
		return new BookInstance.Builder().setId(new Long(1)).setInventoryNumber("1234567898765")
				.setStatus(Status.AVAILABLE)
				.setBook(new Book.Builder().setId(new Long(1)).setAvailability(Availability.READING_ROOM).build())
				.build();
	}
}
