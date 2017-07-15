package ua.training;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.training.command.book.AllBooksCommandTest;
import ua.training.command.book.PostAddBookCommandTest;
import ua.training.command.book.SearchBookByAuthorCommandTest;
import ua.training.command.book.SearchBookByTitleCommandTest;
import ua.training.regex.RegexTest;
import ua.training.service.AuthorServiceTest;
import ua.training.service.BookInstanceServiceTest;
import ua.training.service.BookServiceTest;
import ua.training.service.BookOrderServiceTest;
import ua.training.service.UserServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ RegexTest.class, BookServiceTest.class, AllBooksCommandTest.class, PostAddBookCommandTest.class,
		SearchBookByAuthorCommandTest.class, SearchBookByTitleCommandTest.class, AuthorServiceTest.class,
		BookInstanceServiceTest.class, UserServiceTest.class, BookOrderServiceTest.class })
public class TestSuite {
	/*
	 * empty class
	 */
}