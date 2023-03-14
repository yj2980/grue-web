package web.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;
import web.example.demo.dto.MessageDto;

public class Sens_V2 {
	@Value("${sms.serviceId}")
	private String serviceId;
	

	public SmsResponse send_massage(String phoneNumber, String rand) throws JsonProcessingException {
		String accessKey = "JOJevjFIJr93LP27vO8s";                     						// 개인 인증키
		String secretKey = "AuaIIkuLgaLzTD1jpue69BIvdSqknr41hBkk4Nlq";  										// 2차 인증을 위해 서비스마다 할당되는 service secret
		String timestamp = Long.toString(System.currentTimeMillis()); 	// current timestamp (epoch)
		List<MessageDto> messages = new ArrayList<>();
		messages.add(new MessageDto(phoneNumber, rand));


		SmsRequest request = new SmsRequest("SMS", "COMM", "82", "발신자 번호", "내용", messages);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(request);

		System.out.println(jsonBody);

		try {
			HttpHeaders headers = new HttpHeaders();

			// API HEADER
			headers.setContentType(MediaType.valueOf("application/json"));
			headers.set("x-ncp-apigw-timestamp", timestamp);
			headers.set("x-ncp-iam-access-key", accessKey);
			headers.set("x-ncp-apigw-signature-v2", makeSignature(timestamp);

			HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			SmsResponse response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, SmsResponse.class);

			return response;

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static String makeSignature(String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messags";
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
}
