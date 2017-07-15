package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.dto.ChangePasswordDto;
import ua.training.dto.CredentialsDto;
import ua.training.entity.Reader;
import ua.training.entity.Role;
import ua.training.entity.User;

public class UserServiceTest {

	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private UserDao userDao;
	private UserService userService;

	private List<Reader> generateReadersList() {
		return Arrays.asList(
				new Reader[] {
						new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
								.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
								.setSalt(new byte[] {47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33})
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

	private Optional<Reader> generateReaderOptional() {
		return Optional.of(new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] {47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33})
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876")
				.build());
	}
	
	private Optional<User> generateReaderAsUserOptional() {
		return Optional.of(new Reader.Builder().setId(new Long(1)).setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] {47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33})
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876")
				.build());
	}
	
	private Reader generateReaderForCreation(){
		return new Reader.Builder().setEmail("anna@gmail.com")
				.setPassword("1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be")
				.setSalt(new byte[] {47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33})
				.setRole(Role.READER).setName("Анна").setSurname("Єршак").setPatronymic("Віталіївна")
				.setPhone("+380948596886").setAddress("вул. Сковороди, 3").setReaderCardNumber("KB12456789876")
				.build();
		
	}
	
	private CredentialsDto generateCredentialsDtoWithValidPass(){
		return new CredentialsDto("anna@gmail.com", "library7");
	}
	
	private CredentialsDto generateCredentialsDtoWithInvalidPass(){
		return new CredentialsDto("anna@gmail.com", "sofiya14");
	}
	
	private ChangePasswordDto generateChangePasswordDto(){
		return null;		
	}

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		userDao = mock(UserDao.class);		
	}
	
	private void initUserService(){
		userService = new UserService(daoFactory);		
	}

	private void initUserDaoCreationStubbing() {
		when(daoFactory.createUserDao()).thenReturn(userDao);
	}

	private void initUserDaoCreationWithDaoConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createUserDao(daoConnection)).thenReturn(userDao);
	}

	@Test
	@Ignore
	public void testGetAllReaders() {
		List<Reader> readers = generateReadersList();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationStubbing();
		doReturn(readers).when(userDao).getAllUsers(anyObject());

		List<Reader> actualReaders = userService.getAllReaders();

		assertEquals(readers, actualReaders);
		verify(daoFactory).createUserDao();
		verify(userDao).getAllUsers(Role.READER);
	}

	@Test
	@Ignore
	public void testGetReaderById() {
		Optional<Reader> reader = generateReaderOptional();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationStubbing();
		doReturn(reader).when(userDao).getUserById(anyLong());

		Long readerId = new Long(1);
		Optional<Reader> actualReader = userService.getReaderById(readerId);

		assertEquals(reader.get(), actualReader.get());
		verify(daoFactory).createUserDao();
		verify(userDao).getUserById(readerId);

	}

	@Test
	@Ignore
	public void testGetUserByEmailWithValidPassword() {
		Optional<User> user = generateReaderAsUserOptional();
		CredentialsDto credentialsDto = generateCredentialsDtoWithValidPass();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationStubbing();		
		when(userDao.getUserByEmail(anyString())).thenReturn(user);

		Optional<User> actualUser = userService.getUserByEmail(credentialsDto);

		assertEquals(user.get(), actualUser.get());
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByEmail(credentialsDto.getEmail());
	}
	
	@Test
	@Ignore
	public void testGetUserByEmailWithInvalidPassword() {
		Optional<User> user = generateReaderAsUserOptional();
		CredentialsDto credentialsDto = generateCredentialsDtoWithInvalidPass();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationStubbing();		
		when(userDao.getUserByEmail(anyString())).thenReturn(user);

		Optional<User> expectedUser = Optional.empty();
		Optional<User> actualUser = userService.getUserByEmail(credentialsDto);

		assertEquals(expectedUser, actualUser);
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByEmail(credentialsDto.getEmail());
	}

	@Test
	@Ignore
	public void testCreateReader() {
		Reader reader = generateReaderForCreation();
		
		initObjectsMocking();
		initUserService();
		initUserDaoCreationWithDaoConnectionStubbing();
		
		userService.createReader(reader);
		
		verify(daoFactory).getConnection();
		verify(daoFactory).createUserDao(daoConnection);
		verify(daoConnection).begin();
		verify(userDao).create(reader);
		verify(userDao).createReader(reader);
		verify(daoConnection).commit();
	}

	@Test
	@Ignore
	public void testChangePassword() {

	}
}
