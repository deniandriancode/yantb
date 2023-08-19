package org.fam.blogger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BlogUserDto {

	@NotEmpty(message = "Username cannot be empty")
	private String username;

	@NotEmpty(message = "Email cannot be empty")
	@Email
	private String email;

	@NotEmpty
	@Size(min = 3, max = 30)
	private String password;

	public BlogUserDto(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
