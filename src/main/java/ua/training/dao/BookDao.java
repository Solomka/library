package ua.training.dao;

import java.util.List;

import ua.training.entity.Book;

public interface BookDao extends GenericDao<Book, Long> {

	List<Book> searchByTitle(String title);

	List<Book> searchByAuthorSurname(String authorSurname);
}