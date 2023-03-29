package web.example.demo.dto;

import java.sql.Timestamp;

import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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
		private Timestamp date;
		private String views;

		public static Search toDTO(Board entity) {
			return Search.builder()
					.id(entity.getId())
					.category("미정")
					.title(entity.getTitle())
					.author(entity.getUserID().getUsername())
					.content(entity.getContent())
					.views(String.valueOf(entity.getViews()))
					.date(entity.getCreateDate())
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
		private Timestamp date;

		public static Post toDTO(Board entity) {
			return Post.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.author(entity.getUserID().getUsername())
					.content(entity.getContent())
					.date(entity.getCreateDate())
					.build();
		}
	}

}
