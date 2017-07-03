package ua.training.converter;

import ua.training.controller.dto.BookDto;
import ua.training.model.entity.Availability;
import ua.training.model.entity.Book;

public final class BookDTOBookConverter {

	private BookDTOBookConverter() {

	}

	public static Book fromDtoToEntity(BookDto bookDto) {
		return new Book.Builder().setIsbn(bookDto.getIsbn()).setTitle(bookDto.getTitle())
				.setPublisher(bookDto.getPublisher()).setImprintDate(bookDto.getImprintDate())
				.setAvailability(Availability.SUBSCRIPTION).build();

	}

}
