package web.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import web.example.demo.service.UserService;

@SpringBootTest
public class SignInTest {

	@Autowired
	private UserService userService;
	private boolean result;
	@Test
	void is_correct() {
		String email = "adore2100@naver.com";
		String pw = "";

		if (userService.validationLogin(email, pw).equals("true")) {
			result = true;
		}

		assertTrue(result);
	}

	@Test
	void not_exist_user() {
		String email = "cc90@gmail.com";
		String pw = "ccc";

		if (userService.validationLogin(email, pw).equals("not_exist")) {
			result = false;
		}

		assertFalse(result);
	}

	@Test
	void not_same_pw() {
		String email = "adore2100@naver.com";
		String pw = "ccc";

		if (userService.validationLogin(email, pw).equals("not_same")) {
			result = false;
		}

		assertFalse(result);
	}

}
