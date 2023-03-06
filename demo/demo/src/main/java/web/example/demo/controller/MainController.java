package web.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@GetMapping("signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("community")
	public String community() {
		return "community";
	}

	@GetMapping("write")
	public String write() {
		return "write";
	}

	@PostMapping("signup")
	public String userSignup(User user) {
		userService.user_info_save(user);
		return "home";
	}

	@PostMapping("emailCheck")
	@ResponseBody
	public boolean checkEmail(@RequestParam("email") String email) {
		boolean emailOverlap = userService.checkEmailDuplicate(email);

		return emailOverlap;
	}
}
