package web.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.example.demo.model.User;
import web.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void user_info_save(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	public boolean checkEmailDuplicate(String email) {
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

		return "true";
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
