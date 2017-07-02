

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.model.entity.Availability;
import ua.training.model.entity.Book;
import ua.training.model.entity.Librarian;
import ua.training.model.entity.Role;
import ua.training.model.service.BookService;

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

	@Test
	public void testCreateLibrarian() {
		Librarian librarian = new Librarian.Builder().setId(new Long("1")).setLogin("bla").setPassword("bla")
				.setRole(Role.LIBRARIAN).setEmail("sol.yaremko@gmail.com").setName("A").setSurname("B")
				.setPatronymic("C").build();
		System.out.println("Libraraian: " + librarian);
	}

}
