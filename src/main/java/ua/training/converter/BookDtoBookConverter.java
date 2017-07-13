package ua.training.converter;

import java.util.ArrayList;
import java.util.List;

import ua.training.dto.BookDto;
import ua.training.entity.Author;
import ua.training.entity.Book;

public final class BookDtoBookConverter {

	private BookDtoBookConverter() {

	}

	public static Book toBook(BookDto bookDto) {
		return new Book.Builder().setIsbn(bookDto.getIsbn()).setTitle(bookDto.getTitle())
				.setAvailability(bookDto.getAvailability()).setPublisher(bookDto.getPublisher())
				.setAuthors(getBookAuthors(bookDto.getAuthorsIds())).build();
	}

	private static List<Author> getBookAuthors(String[] authorsIds) {
		List<Author> bookAuthors = new ArrayList<>();

		for (String authorId : authorsIds) {
			bookAuthors.add(new Author.Builder().setId(new Long(authorId)).build());
		}
		return bookAuthors;
	}
}
