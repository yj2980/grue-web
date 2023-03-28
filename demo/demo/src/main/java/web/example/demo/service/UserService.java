package web.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.example.demo.dto.UserDto;
import web.example.demo.model.User;
import web.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void saveUserInfo(UserDto userDto) {
		userRepository.save(toEntity(userDto));
	}

	private User toEntity(UserDto userDto) {
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());

		return userDto.toUser(encodedPassword);
	}


	public boolean isEmailDuplicate(String email) {
		return userRepository.existsByEmail(email);
	}

	public String validationLogin(String email, String password) {
		User user_info = findUserByEmail(email);

		if (user_info == null) {
			return "not_exist";
		}

		if (!passwordEncoder.matches(password, String.valueOf(user_info.getPassword()))) {
			return "not_same";
		}

		return user_info.getUsername();
	}

	/*
		JPA SQL QUERY
	 */

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findEmailByPhoneNumber(String phoneNumber) {

		return userRepository.findByPhoneNumber(phoneNumber);
	}

}
