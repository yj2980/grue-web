package web.example.demo.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import net.bytebuddy.asm.Advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.example.demo.model.Board;
import web.example.demo.model.User;


public class BoardDto {
	@Getter
	@Setter
	@ToString
	public class Create {

		private int id;
		private String title;
		private String content;
		private String author;
		private String category;

		public Board toBoard(User user) {
			return Board.builder()
					.id(id)
					.userID(user)
					.title(title)
					.content(content)
					.build();
		}
	}

	@Getter
	@Setter
	@Builder
	public static class Search {
		private int id;
		private String title;
		private String author;
		private String content;
		private String category;
		private LocalDateTime modifiedDate;
		private String views;

		public static Search toDTO(Board entity) {
			return Search.builder()
					.id(entity.getId())
					.category("미정")
					.title(entity.getTitle())
					.author(entity.getUserID().getUsername())
					.content(entity.getContent())
					.modifiedDate(entity.getModifiedDate())
					.views(String.valueOf(entity.getViews()))
					.build();
		}
	}

	@Getter
	@Setter
	@Builder
	public static class Post {
		private int id;
		private String title;
		private String content;
		private String author;
		private LocalDateTime modifiedDate;

		public static Post toDTO(Board entity) {
			return Post.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.author(entity.getUserID().getUsername())
					.modifiedDate(entity.getModifiedDate())
					.content(entity.getContent())
					.build();
		}
	}

}
