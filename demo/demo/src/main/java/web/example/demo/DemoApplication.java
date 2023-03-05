package web.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import web.example.demo.model.Board;
import web.example.demo.model.User;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		User user = new User();
		Board board = new Board();

		SpringApplication.run(DemoApplication.class, args);
	}

}
