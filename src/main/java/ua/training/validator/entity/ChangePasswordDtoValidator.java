package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.controller.dto.ChangePasswordDto;
import ua.training.locale.Message;

public class ChangePasswordDtoValidator implements Validator<ChangePasswordDto> {

	private static final String PASSWORD_REGEX = "^[\\wА-ЯІЇЄа-яіїє]{8,14}$";

	private ChangePasswordDtoValidator() {
	}

	private static class Holder {
		static final ChangePasswordDtoValidator INSTANCE = new ChangePasswordDtoValidator();
	}

	public static ChangePasswordDtoValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(ChangePasswordDto dto) {
		List<String> errors = new ArrayList<>();

		checkOldPassword(dto, errors);
		checkNewPassword(dto, errors);
		checkConfirmPassword(dto, errors);
		checkNewConfirmPassword(dto, errors);

		return errors;
	}

	private void checkOldPassword(ChangePasswordDto dto, List<String> errors) {
		if (!dto.getOldPassword().matches(PASSWORD_REGEX)) {
			errors.add(Message.INVALID_OLD_PASSWORD);
		}
	}

	private void checkNewPassword(ChangePasswordDto dto, List<String> errors) {
		if (!dto.getNewPassword().matches(PASSWORD_REGEX)) {
			errors.add(Message.INVALID_NEW_PASSWORD);
		}
	}

	private void checkConfirmPassword(ChangePasswordDto dto, List<String> errors) {
		if (!dto.getConfirmPassword().matches(PASSWORD_REGEX)) {
			errors.add(Message.IVALID_CONFIRM_PASSWORD);
		}
	}

	private void checkNewConfirmPassword(ChangePasswordDto dto, List<String> errors) {
		if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
			errors.add(Message.INVALID_NEW_CONFIRM_PASSWORD);
		}
	}
}
