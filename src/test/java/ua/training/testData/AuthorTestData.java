package ua.training.testData;

import java.util.Arrays;
import java.util.List;

import ua.training.entity.Author;

public final class AuthorTestData {

	private AuthorTestData() {

	}

	public static Author generateAuthor() {
		return new Author.Builder().setName("Daniel").setSurname("Keyes").setCountry("America").build();
	}

	public static List<Author> generateAuthorsList() {
		return Arrays.asList(new Author[] {
				new Author.Builder().setId(new Long(1)).setName("Daniel").setSurname("Keyes").setCountry("America")
						.build(),
				new Author.Builder().setId(new Long(2)).setName("Stephen").setSurname("King").setCountry("America")
						.build() });
	}

}
