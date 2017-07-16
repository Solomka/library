package ua.training;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.training.controller.command.auth.PostLoginCommandTest;
import ua.training.controller.command.author.PostAddAuthorCommandTest;
import ua.training.controller.command.book.AllBooksCommandTest;
import ua.training.controller.command.book.PostAddBookCommandTest;
import ua.training.controller.command.book.SearchBookByAuthorCommandTest;
import ua.training.controller.command.book.SearchBookByTitleCommandTest;
import ua.training.controller.command.bookInstance.PostAddBookInstanceCommandTest;
import ua.training.controller.command.bookOrder.UnfulfilledOrdersCommandTest;
import ua.training.regex.RegexTest;
import ua.training.service.AuthorServiceTest;
import ua.training.service.BookInstanceServiceTest;
import ua.training.service.BookOrderServiceTest;
import ua.training.service.BookServiceTest;
import ua.training.service.UserServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ RegexTest.class, BookServiceTest.class, AllBooksCommandTest.class, PostAddBookCommandTest.class,
		SearchBookByAuthorCommandTest.class, SearchBookByTitleCommandTest.class, AuthorServiceTest.class,
		PostAddAuthorCommandTest.class, BookInstanceServiceTest.class, PostAddBookInstanceCommandTest.class,
		UserServiceTest.class, PostLoginCommandTest.class, BookOrderServiceTest.class, UnfulfilledOrdersCommandTest.class })
public class TestSuite {
	/*
	 * empty class
	 */
}