package web.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import web.example.demo.model.User;
import web.example.demo.service.SensService;
import web.example.demo.service.UserService;
import web.example.demo.util.SmsResponse;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private SensService smsService;

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
	@GetMapping("findAccount")
	public String findAccount() {
		return "findAccount";
	}

	@GetMapping("community")
	public String community() {
		return "community";
	}

	@GetMapping("write")
	public String write() {
		return "write";
	}

	// 회원가입 정보 USER Table에 저장
	@PostMapping("signup")
	public String userSignup(User user) {
		userService.user_info_save(user);
		return "home";
	}

	//login 검증
	@PostMapping("loginCheck")
	@ResponseBody
	public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
		String result = "";

		try {
			result = userService.validationLogin(email, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// email 중복 검증
	@PostMapping("emailDuplicationCheck")
	@ResponseBody
	public boolean checkEmail(@RequestParam("email") String email) {
		boolean result = false;

		try {
			result = userService.checkEmailDuplicate(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@PostMapping("findEmailByPhoneNumber")
	public ResponseEntity<SmsResponse> findEmail(@RequestParam("phoneNumber") String phoneNumber) throws JsonProcessingException {
		String result;
		User user = userService.findEmailByPhoneNumber(phoneNumber);

		if (user == null) {
			return null;
		}

		SmsResponse data = smsService.send_massage(phoneNumber, user.getEmail());

		return ResponseEntity.ok().body(data);
	}

}
