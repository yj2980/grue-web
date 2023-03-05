package web.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import web.example.demo.model.User;
import web.example.demo.service.UserService;

@Controller
public class MainController {
	@Autowired
	private UserService userService;

	@GetMapping("")
	public String home() {
		return "home";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@GetMapping("sign_in")
	public String sign_in() {
		return "sign_in";
	}

	@GetMapping("community")
	public String community() {
		return "community";
	}

	@GetMapping("write")
	public String write() {
		return "write";
	}

	@PostMapping("sign_in")
	public String userSignIn(User user) {
		userService.user_info_save(user);
		return "home";
	}
}
