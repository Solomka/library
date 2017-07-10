package ua.training.controller.dto;

import ua.training.entity.IBuilder;
import ua.training.entity.User;

public class ChangePasswordDto {

	private User user;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	public ChangePasswordDto() {

	}

	public static class Builder implements IBuilder<ChangePasswordDto> {

		private ChangePasswordDto changePasswordDto = new ChangePasswordDto();

		public Builder setUser(User user) {
			changePasswordDto.user = user;
			return this;
		}

		public Builder setOldPassword(String oldPassword) {
			changePasswordDto.oldPassword = oldPassword;
			return this;
		}

		public Builder setNewPassword(String newPassword) {
			changePasswordDto.newPassword = newPassword;
			return this;
		}

		public Builder setConfirmPassword(String confirmNewPassword) {
			changePasswordDto.confirmPassword = confirmNewPassword;
			return this;
		}

		@Override
		public ChangePasswordDto build() {
			return changePasswordDto;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmNewPassword) {
		this.confirmPassword = confirmNewPassword;
	}

}
