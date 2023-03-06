package web.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.example.demo.model.User;
import web.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void user_info_save(User user) {
		userRepository.save(user);
	}

	public boolean checkEmailDuplicate(String email) {
		return userRepository.existsByEmail(email);
	}

}
