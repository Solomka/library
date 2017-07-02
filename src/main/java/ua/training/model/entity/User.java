package ua.training.model.entity;

import java.io.Serializable;

public abstract class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String password;
	private Role role;
	private byte [] salt;

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

		public T setEmail(String email) {
			user.email = email;
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
		
		public T setSalt(byte[] salt) {
			user.salt = salt;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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

		if ((email != null) ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		return ((password != null) ? password.equals(user.password) : user.password == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", email=").append(email).append(", password=").append(password)
				.append(", role=").append(role).append("]");
		return builder.toString();
	}

}
