package ua.training.dao;

import ua.training.entity.Author;

public interface AuthorDao extends GenericDao<Author, Long>, AutoCloseable {

	void close();
}
