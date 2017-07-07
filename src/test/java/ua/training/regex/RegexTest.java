package ua.training.regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegexTest {

	@Test
	public void passwordRegex() {
		String pass = "librarian12";

		boolean actual = pass.matches("^[\\wА-ЯІЇЄа-яіїє]{8,14}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void emailRegex() {
		String pass = "ann@gmail.com";

		boolean actual = pass.matches("^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void addressRegex() {
		// String pass = "<script[^>]*>(.*?)</script[^>]*>";
		String pass = "вул. Захарія Ониського, 12";

		boolean actual = pass.matches("^[a-zA-ZА-ЯІЇЄа-яіїє\\d\\s.’'-,]{3,100}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	// @Test
	public void phoneRegex() {
		String pass = "2751256";

		boolean actual = pass.matches("^(\\+)?\\d{7,13}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void generalStringRegex() {
		String pass = "Олександра Андріївна Євшан";

		boolean actual = pass.matches("[a-zA-ZА-ЯІЇЄа-яіїє\\s’'-]{3,100}");
		boolean expected = true;

		assertEquals(expected, actual);
	}
	
	/*@Test
	public void publisherRegex() {
		String pass = "12asda";

		boolean actual = pass.matches("^[a-zA-ZА-ЯІЇЄа-яіїє\\d]([a-zA-ZА-ЯІЇЄа-яіїє\\s’'-])+{3,100}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}*/
	
	

	@Test
	public void readerCardNumberRegex() {
		String pass = "КВ09715049";

		boolean actual = pass.matches("[a-zA-ZА-ЯІЇЄа-яіїє\\d]{10,13}");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void TitleRegex() {
		String pass = "Золоте_озеро 12'";

		boolean actual = pass.matches("[\\wА-ЯІЇЄа-яіїє\\d\\s’'-]{3,100}");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void ISBNRegex() {
		String pass = "1234567890987";

		boolean actual = pass.matches("\\d{13}");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void InventoryNumberRegex() {
		String pass = "1234567890987";

		boolean actual = pass.matches("\\d{7,13}");
		boolean expected = true;

		assertEquals(expected, actual);
	}

	@Test
	public void JScriptRegex() {
		String pass = "<script asd=asd></script>";

		boolean actual = pass.matches("<script[^>]*>(.*?)</script[^>]*>");
		boolean expected = true;

		assertEquals(expected, actual);
	}
}
