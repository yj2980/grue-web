package web.example.demo.util;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.example.demo.dto.MessageDto;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SmsRequest {
	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private String content;
	private List<MessageDto> messages;
}
