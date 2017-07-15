package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.dto.ChangePasswordDto;
import ua.training.dto.CredentialsDto;
import ua.training.entity.Reader;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.testData.UserTestData;

public class UserServiceTest {

	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private UserDao userDao;
	private UserService userService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		userDao = mock(UserDao.class);
	}

	private void initUserService() {
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
	// @Ignore
	public void testGetAllReaders() {
		List<Reader> readers = UserTestData.generateReadersList();

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
	// @Ignore
	public void testGetReaderById() {
		Optional<Reader> reader = UserTestData.generateReaderOptional();

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
	// @Ignore
	public void testGetUserByEmailWithValidPassword() {
		Optional<User> user = UserTestData.generateReaderAsUserOptional();
		CredentialsDto credentialsDto = UserTestData.generateCredentialsDtoWithValidPass();

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
	// @Ignore
	public void testGetUserByEmailWithInvalidPassword() {
		Optional<User> user = UserTestData.generateReaderAsUserOptional();
		CredentialsDto credentialsDto = UserTestData.generateCredentialsDtoWithInvalidPass();

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
	// @Ignore
	public void testCreateReader() {
		Reader reader = UserTestData.generateReaderForCreation();

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
	// @Ignore
	public void testChangePasswordWithValidOldPassword() {
		ChangePasswordDto changePasswordDto = UserTestData.generateChangePasswordDtoWithValidPass();
		Optional<User> userOptional = UserTestData.generateReaderAsUserOptional();
		User user = userOptional.get();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationWithDaoConnectionStubbing();
		when(userDao.getUserById(anyLong())).thenReturn(userOptional);

		boolean expectedResult = true;
		boolean actualResult = userService.changePassword(changePasswordDto);

		assertEquals(expectedResult, actualResult);
		verify(daoFactory).getConnection();
		verify(daoFactory).createUserDao(daoConnection);
		verify(daoConnection).begin();
		verify(userDao).getUserById(changePasswordDto.getUserId());
		verify(userDao).update(user);
		verify(daoConnection).commit();
	}

	@Test
	// @Ignore
	public void testChangePasswordWithInvalidOldPassword() {
		ChangePasswordDto changePasswordDto = UserTestData.generateChangePasswordDtoWithInvalidPass();
		Optional<User> userOptional = UserTestData.generateReaderAsUserOptional();

		initObjectsMocking();
		initUserService();
		initUserDaoCreationWithDaoConnectionStubbing();
		when(userDao.getUserById(anyLong())).thenReturn(userOptional);

		boolean expectedResult = false;
		boolean actualResult = userService.changePassword(changePasswordDto);

		assertEquals(expectedResult, actualResult);
		verify(daoFactory).getConnection();
		verify(daoFactory).createUserDao(daoConnection);
		verify(daoConnection).begin();
		verify(userDao).getUserById(changePasswordDto.getUserId());
	}
}
