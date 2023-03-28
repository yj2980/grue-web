package web.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import web.example.demo.model.User;

@Getter
@Setter
public class UserDto {

	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private String role;

	public User toUser(String encodedPassword) {
		return User.builder()
				.username(username)
				.password(encodedPassword)
				.email(email)
				.phoneNumber(phoneNumber)
				.role(role)
				.build();
	}
}
