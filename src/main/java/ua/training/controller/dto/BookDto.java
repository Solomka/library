package ua.training.controller.dto;

import java.time.LocalDate;

public class BookDto {

	private String isbn;
	private String title;
	private String publisher;
	private LocalDate imprintDate;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public LocalDate getImprintDate() {
		return imprintDate;
	}
	public void setImprintDate(LocalDate imprintDate) {
		this.imprintDate = imprintDate;
	}
	
	
	
}
