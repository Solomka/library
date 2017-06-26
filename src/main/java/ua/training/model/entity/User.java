package ua.training.model.entity;

import java.io.Serializable;

public abstract class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String password;
	private Role role;

	public User() {

	}

	public abstract static class Builder<T extends Builder<T>> {
		protected User user;

		public Builder() {
		}

		/** solution for the unchecked cast warning. */
		public abstract T getThis();

		public T setId(Long id) {
			user.id = id;
			return getThis();
		}

		public T setLogin(String login) {
			user.login = login;
			return getThis();
		}

		public T setPassword(String password) {
			user.password = password;
			return getThis();
		}

		public T setRole(Role role) {
			user.role = role;
			return getThis();
		}

		public User build() {
			return user;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}

		User user = (User) obj;

		if ((login != null) ? !login.equals(user.login) : user.login != null) {
			return false;
		}
		return ((password != null) ? password.equals(user.password) : user.password == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", login=").append(login).append(", password=").append(password)
				.append(", role=").append(role).append("]");
		return builder.toString();
	}

}
