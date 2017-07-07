package ua.training.locale;

public final class Message {

	
	private Message() {

	}

	public static String LOGGED_IN_AS = "library.loggedIn";
	public static String PAGE_NOT_FOUND_ERROR = "library.error.pageNotFoundError";
	public static String SERVER_ERROR = "library.error.serverError";
	public static String DIRECT_VIEW_ACCESS_ERROR = "library.error.directViewAccessError";
	public static String UNAUTHORIZED_ACCESS_ERROR = "library.error.authorizedAccessError";
	public static String SUCCESS_BOOK_ADDITION = "library.success.addBook";
	public static String BOOK_IS_NOT_FOUND = "library.error.bookIsNotFound";
	public static String NO_AVAILABLE_BOOK_INSTANCES = "library.error.noAvailableBookIntstances";
	
	public static String SUCCESS_AUTHOR_ADDITION = "library.success.addAuthor";
	
	public static String SUCCESS_BOOK_INSTANCE_ADDITION = "library.success.addBookInstance";
	
	public static String INVALID_NAME_INPUT = "library.error.invalidName";
	public static String INVALID_SURNAME_INPUT = "library.error.invalidSurname";
	public static String INVALID_PATRONYMIC_INPUT = "library.error.invalidPatronymic";
	public static String INVALID_PUBLISHER_INPUT = "library.error.invalidPublisher";
	public static String INVALID_COUNTRY_INPUT = "library.error.invalidCountry";

	public static String INVALID_EMAIL = "library.error.invalidEmail";
	public static String INVALID_PASS = "library.error.invalidPass";
	public static String INVALID_CREDENTIALS = "library.error.invalidCredentials";

	public static String INVALID_ISBN = "library.error.invalidISBN";
	public static String INVALID_TITLE = "library.error.invalidTitle";
	public static  String INVALID_BOOK_AUTHORS_SELECTION = "library.error.invalidBookAuthorsSelection";

	public static String INVALID_INVENTORY_NUMBER = "library.error.invalidIInventoryNumber";

	public static String INVALID_PHONE = "library.error.invalidPhone";
	public static String INVALID_ADDRESS = "library.error.invalidAddress";
	public static String INVALID_READER_CARD_NUMBER = "library.error.invalidReaderCardNumber";
	
	public static String AVAILABILITY_READING_ROOM = "library.book.availability.readingRoom";
	public static String AVAILABILITY_SUBSCRIPTION = "library.book.availability.subscription";

	
	public static String STATUS_AVAILABLE = "library.bookInstance.status.available";
	public static String STATUS_UNAVAILABLE = "library.bookInstance.status.unavailable";
	
	public static String ROLE_READER = "library.user.role.reader";
	public static String ROLE_LIBRARIAN = "library.user.role.librarian";
	
}
