package web.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import web.example.demo.dto.CityDto;
import web.example.demo.dto.UserDto;
import web.example.demo.model.User;
import web.example.demo.service.BoardService;
import web.example.demo.service.SensService;
import web.example.demo.service.UserService;
import web.example.demo.util.SmsResponse;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
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

	@GetMapping("planningMap")
	public String planningMap() {
		return "planningMap";
	}

	@GetMapping("planningThroughMap")
	public String planningThroughMap() {
		return "planningThroughMap";
	}

	@GetMapping("chooseCountry")
	public String chooseCountry(Model model) {
		List<CityDto.ShowCityDTO> city = boardService.findCityList();

		model.addAttribute("cityList", city);

		return "chooseCountry";
	}

	@GetMapping("cityPostWriteByAdmin")
	public String cityPostWrite() {
		return "cityPostWriteByAdmin";
	}
	@PostMapping("writingCity")
	public String postCityList(CityDto.Create city, MultipartFile file) throws IOException {
		boardService.saveCityInfo(city, file);

		return "redirect:/chooseCountry";
	}
	@PostMapping("chooseCountry/selectCounty")
	public String selectCountry(@RequestParam("countryKeyword") String keyword) {

		System.out.println("[selectCountry]\n******************\n key : " + keyword);

		return "redirect:/chooseCountry";
	}
	@GetMapping("write")
	public String write() {
		return "write";
	}

	// 회원가입 정보 USER Table에 저장
	@PostMapping("signup")
	public String userSignup(UserDto userDto) {
		userService.saveUserInfo(userDto);

		return "home";
	}

	//login 검증
	@PostMapping("loginCheck")
	@ResponseBody
	public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
		String result = "";

		try {
			result = userService.validateLogin(email, password);

			System.out.println("usercheck 결과 확인 \n ------------------------\n " + result);

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
			result = userService.isEmailDuplicate(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@PostMapping("findEmailByPhoneNumber")
	public ResponseEntity<SmsResponse> findEmail(@RequestParam("phoneNumber") String phoneNumber) throws JsonProcessingException {
		User user = userService.findEmailByPhoneNumber(phoneNumber);

		// 가입된 email이 없을 경우
		if (user == null) {
			return null;
		}

		SmsResponse data = smsService.send_massage(phoneNumber, user.getEmail());

		return ResponseEntity.ok().body(data);
	}

}
