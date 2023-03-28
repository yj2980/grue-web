package web.example.demo.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.example.demo.model.Board;
import web.example.demo.model.User;

@Getter
@Setter
@Builder
public class BoardDto {
	private String title;
	private String author;
	private String content;
	private String category;
	private Timestamp date;
	private String views;

	public String toString() {
		return "boardForm { " +
				"title ='" + title + "'\n" +
				"author ='" + author + "'\n" +
				"content ='" + content + "'\n" +
				"category ='" + category + "' }";
	}
	public static BoardDto toDTO(Board entity) {
		return BoardDto.builder()
				.category("미정")
				.title(entity.getTitle())
				.author(entity.getUserID().getUsername())
				.views(String.valueOf(entity.getViews()))
				.date(entity.getCreateDate())
				.build();
	}

	public Board toBoard(User user) {
		return Board.builder()
				.userID(user)
				.title(title)
				.content(content)
				.build();
	}
}
