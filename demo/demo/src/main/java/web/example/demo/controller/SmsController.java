package web.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import web.example.demo.service.SensService;
import web.example.demo.util.SmsResponse;

@Controller
@RequiredArgsConstructor
public class SmsController {
	String randomNum;

	private final SensService smsService;

	@PostMapping("sendPhoneNumber")
	public ResponseEntity<SmsResponse> smsCheck(@RequestParam("phoneNumber") String phoneNumber) throws JsonProcessingException {
		randomNum = smsService.createRandomNumber();

		SmsResponse data = smsService.send_massage(phoneNumber, randomNum);

		return ResponseEntity.ok().body(data);
	}

	@PostMapping("checkVerificationCode")
	@ResponseBody
	public boolean test(@RequestParam("code") String code)  {
		boolean isCheck = code.equals(randomNum);

		return isCheck;
	}
}
