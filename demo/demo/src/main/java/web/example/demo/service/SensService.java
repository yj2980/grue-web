package web.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import web.example.demo.dto.MessageDto;
import web.example.demo.util.SmsRequest;
import web.example.demo.util.SmsResponse;

@Service
@Transactional
public class SensService {
	int MAX = 10;
	@Value("${api.sms[0]}")
	private String serviceId;
	@Value("${api.sms[1]}")
	private String accessKey;
	@Value("${api.sms[2]}")
	private String secretKey;

	public SmsResponse send_massage(String phoneNumber, String rand) throws JsonProcessingException {
		phoneNumber = replacePhoneNumber(phoneNumber);

		String timestamp = Long.toString(System.currentTimeMillis());
		List<MessageDto> messages = new ArrayList<>();
		messages.add(new MessageDto(phoneNumber, rand));
		SmsResponse response = null;


		SmsRequest request = new SmsRequest("SMS", "COMM", "82", "01033712610", "내용", messages);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(request);

		try {
			HttpHeaders headers = new HttpHeaders();

			// API HEADER
			headers.setContentType(MediaType.valueOf("application/json"));
			headers.set("x-ncp-apigw-timestamp", timestamp);
			headers.set("x-ncp-iam-access-key", this.accessKey);
			headers.set("x-ncp-apigw-signature-v2", makeSignature(timestamp));

			HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

			response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, SmsResponse.class);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return response;
	}

	public String makeSignature(String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;


		String message = new StringBuilder()
				.append(method)
				.append(space)
				.append(url)
				.append(newLine)
				.append(timestamp)
				.append(newLine)
				.append(accessKey)
				.toString();

		SecretKeySpec signingKey;
		String encodeBase64String;
		try {
			signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
		} catch (UnsupportedEncodingException e) {
			encodeBase64String = e.toString();
		}


		return encodeBase64String;
	}

	private String replacePhoneNumber(String phoneNumber) {
		phoneNumber = phoneNumber.replaceAll("-", "");

		return phoneNumber;
	}

	public String createRandomNumber() {
		Random random = new Random();
		String number = "";

		for (int i = 0; i < 6; i++) {
			number += Integer.toString(random.nextInt(MAX));
		}

		System.out.println("회원가입 문자 인증 => " + number);

		return number;
	}
}
