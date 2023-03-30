package web.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.example.demo.model.Board;
import web.example.demo.model.User;
import web.example.demo.util.BoardCategoryEnum;

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
					.category(category)
					.build();
		}
	}

	@Getter
	@Setter
	@Builder
	public static class ShowBoardDTO {
		private int id;
		private String title;
		private String author;
		private String content;
		private String category;
		private LocalDateTime modifiedDate;
		private String views;

		public static ShowBoardDTO toDTO(Board entity) {
			return ShowBoardDTO.builder()
					.id(entity.getId())
					.category(BoardCategoryEnum.findCategoryBySelectValue(entity.getCategory()))
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
