package ua.training.dao.jdbc;

/**
 * Class that represents db data tables columns' names
 * 
 * @author Solomka
 *
 */
public final class Column {

	private Column() {

	}

	// user table columns' names
	static String USER_ID = "id_user";
	static String USER_EMAIL = "email";
	static String USER_PASSWORD = "password";
	static String USER_ROLE = "role";
	static String USER_SALT = "salt";

	// reader table columns' names
	static String READER_NAME = "name";
	static String READER_SURNAME = "surname";
	static String READER_PATRONYMIC = "patronymic";
	static String READER_PHONE = "phone";
	static String READER_ADDRESS = "address";
	static String READER_READER_CARD_NUMBER = "reader_card_number";

	// librarian table columns' names
	static String LIBRARIAN_NAME = "name";
	static String LIBRARIAN_SURNAME = "surname";
	static String LIBRARIAN_PATRONYMIC = "patronymic";

	// book table columns' names
	static String BOOK_ID = "id_book";
	static String BOOK_ISBN = "isbn";
	static String BOOK_TITLE = "title";
	static String BOOK_PUBLISHER = "publisher";
	static String BOOK_AVAILABILITY = "availability";

	// author table columns' names
	static String AUTHOR_ID = "id_author";
	static String AUTHOR_NAME = "name";
	static String AUTHOR_SURNAME = "surname";
	static String AUTHOR_COUNTRY = "country";

	// book_instance table columns' names
	static String BOOK_INSTANCE_ID = "id_book_instance";
	static String BOOK_INSTANCE_STATUS = "status";
	static String BOOK_INSTANCE_INVENTORY_NUMBER = "inventory_number";
	static String BOOK_INSTANCE_ID_BOOK = "id_book";

	// book_order table columns' names
	static String ORDER_ID = "id_order";
	static String ORDER_CREATION_DATE = "creation_date";
	static String ORDER_FULFILMENT_DATE = "fulfilment_date";
	static String ORDER_PICKUP_DATE = "pickup_date";
	static String ORDER_RETURN_DATE = "return_date";
	static String ORDER_ACTUAL_RETURN_DATE = "actual_return_date";
	static String ORDER_ID_BOOK_INSTANCE = "id_book_instance";
	static String ORDER_ID_READER = "id_reader";
	static String ORDER_ID_LIBRARIAN = "id_librarian";
}
