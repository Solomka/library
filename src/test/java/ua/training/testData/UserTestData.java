package ua.training.testData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ua.training.dto.ChangePasswordDto;
import ua.training.dto.CredentialsDto;
import ua.training.entity.Reader;
import ua.training.entity.Role;
import ua.training.entity.User;

public final class UserTestData {

	private UserTestData() {

	}

	public static List<Reader> generateReadersList() {
		return Arrays.asList(
				new Reader[] {
						new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
								.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
								.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31,
										-85, 33 })
								.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
								.setPhone("+380948596886").setAddress("вул. Сковороди, 3")
								.setReaderCardNumber("KB12456789876").build(),
						new Reader.Builder().setId(new Long(2)).setEmail("petro@gmail.com")
								.setPassword("222de87818bfebfdcb92f18d2e5e43b69e4f70783e0cd66c481bc75d0d686585")
								.setSalt(new byte[] { 76, 7, 67, 4, 7, -45, 127, 17, -52, -39, -76, -29, -40, 76, 123,
										46 })
								.setRole(Role.READER).setName("Поліна").setSurname("Єршак").setPatronymic("Олегівна")
								.setPhone("+38056784554").setAddress("вул. Сковороди, 4")
								.setReaderCardNumber("KB89098700987").build() });
	}

	public static Reader generateReader() {
		return new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33 })
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876").build();
	}

	public static Optional<Reader> generateReaderOptional() {
		return Optional.of(new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33 })
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876")
				.build());
	}

	public static User generateReaderAsUser() {
		return new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33 })
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876").build();
	}

	public static Optional<User> generateReaderAsUserOptional() {
		return Optional.of(new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33 })
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876")
				.build());
	}

	public static Reader generateReaderForCreation() {
		return new Reader.Builder().setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] { 47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33 })
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876").build();

	}

	public static CredentialsDto generateCredentialsDtoWithValidPass() {
		return new CredentialsDto("anna@gmail.com", "library7");
	}

	public static CredentialsDto generateCredentialsDtoWithInvalidPass() {
		return new CredentialsDto("anna@gmail.com", "sofiya14");
	}

	public static ChangePasswordDto generateChangePasswordDtoWithValidPass() {
		return new ChangePasswordDto.Builder().setUserId(new Long(1)).setOldPassword("library7")
				.setNewPassword("reader77").setConfirmPassword("reader77").build();
	}

	public static ChangePasswordDto generateChangePasswordDtoWithInvalidPass() {
		return new ChangePasswordDto.Builder().setUserId(new Long(1)).setOldPassword("viktoria7")
				.setNewPassword("reader77").setConfirmPassword("reader77").build();
	}

}
