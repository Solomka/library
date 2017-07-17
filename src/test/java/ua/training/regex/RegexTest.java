package ua.training.regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegexTest {

	@Test
	// @Ignore
	public void testIsbnRegexSuccess() {
		String pass = "1234567890987";

		boolean actual = pass.matches("^\\d{13}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testIsbnRegexFailure() {
		String pass = "1234567890OA7";

		boolean actual = pass.matches("^\\d{13}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testInventoryNumberRegexSuccess() {
		String pass = "1234567890987";

		boolean actual = pass.matches("^\\d{7,13}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testInventoryNumberRegexFailure() {
		String pass = "12345678909OA";

		boolean actual = pass.matches("^\\d{7,13}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testReaderCardNumberRegexSuccess() {
		String pass = "КВ0097150497";

		boolean actual = pass.matches("^[A-ZА-ЯІЇЄ]{2}\\d{8,11}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testReaderCardNumberRegexFailure() {
		String pass = "КВA097150497";

		boolean actual = pass.matches("^[A-ZА-ЯІЇЄ]{2}\\d{8,11}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testEmailRegexSuccess() {
		String pass = "ann@gmail.com";

		boolean actual = pass.matches("^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testEmailRegexFailure() {
		String pass = "anngmail.com";

		boolean actual = pass.matches("^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPhoneRegexSuccess() {
		String pass = "+380958039884";

		boolean actual = pass.matches("^(\\+)?\\d{7,13}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPhoneRegexFailure() {
		String pass = "+38A0958039884";

		boolean actual = pass.matches("^(\\+)?\\d{7,13}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPasswordRegexSuccess() {
		String pass = "uioikj98";
		boolean actual = pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,14}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPasswordRegexFailure() {
		String pass = "89877897 _%89";

		boolean actual = pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,14}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testAddressRegexSuccess() {
		String pass = "вул. Сковороди, 12";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d](?=.*[a-zA-ZА-ЯІЇЄа-яіїє]{2,99})[a-zA-ZА-ЯІЇЄа-яіїє\\d\\s/\\.’'-,]*$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testAddressRegexFailure() {
		String pass = "12345";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d](?=.*[a-zA-ZА-ЯІЇЄа-яіїє]{2,99})[a-zA-ZА-ЯІЇЄа-яіїє\\d\\s/\\.’'-,]*$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testTitleRegexSuccess() {
		String pass = "Золоте_озеро 12'";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d]+[\\wА-ЯІЇЄа-яіїє\\s’'-]{2,99}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testTitleRegexFailure() {
		String pass = "   ";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d]+[\\wА-ЯІЇЄа-яіїє\\s’'-]{2,99}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPublisherRegexSuccess() {
		String pass = "World 12-3";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d]+[\\wА-ЯІЇЄа-яіїє\\s’'-]{2,99}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testPublisherRegexFailure() {
		String pass = "   '-";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє\\d]+[\\wА-ЯІЇЄа-яіїє\\s’'-]{2,99}$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testCountryRegexSuccess() {
		String pass = "Велика Британія";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testCountryRegexFailure() {
		String pass = "123 ";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testNameSurnamePatronymicRegexSuccess() {
		String pass = "В'ячеслав";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	// @Ignore
	public void testNameSurnamePatronymicRegexFailure() {
		String pass = " 'ячеслав";

		boolean actual = pass.matches("^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$");
		boolean expected = false;

		assertEquals(expected, actual);
	}

}
