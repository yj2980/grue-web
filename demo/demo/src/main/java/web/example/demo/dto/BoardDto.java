package web.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import web.example.demo.model.Board;
import web.example.demo.model.User;

@Getter
@Setter
public class BoardDto {
	private String title;
	private String author;
	private String content;
	private String category;

	public BoardDto(String title, String author, String content, String category) {
		this.title = title;
		this.author = author;
		this.content = content;
		this.category = category;
	}

	public String toString() {
		return "boardForm { " +
				"title ='" + title + "'\n" +
				"author ='" + author + "'\n" +
				"content ='" + content + "'\n" +
				"category ='" + category + "' }";
	}

	public Board toBoard(User user) {
		return Board.builder()
				.userID(user)
				.title(title)
				.content(content)
				.build();
	}
}
