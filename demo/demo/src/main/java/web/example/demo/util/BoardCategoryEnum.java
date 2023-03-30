package web.example.demo.util;

import java.util.Arrays;

public enum BoardCategoryEnum {
	UNKNOWN("unknown", "unknown"),
	GENERAL("general_board", "자유"),
	QUESTION("question_board", "질문"),
	REVIEW("review_board", "후기"),
	SHARING("sharingInformation_board", "정보공유");

	private String selectValue;
	private String categoryValue;

	BoardCategoryEnum(String selectValue, String categoryValue) {
		this.selectValue = selectValue;
		this.categoryValue = categoryValue;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public String getCategoryValue() {
		return categoryValue;
	}


	public static String findCategoryBySelectValue(String select) {
		return Arrays.stream(values())
				.filter(v -> v.selectValue.equals(select))
				.findAny()
				.orElse(UNKNOWN)
				.getCategoryValue();
	}

	public static String findSelectByCategoryValue(String category) {
		return Arrays.stream(values())
				.filter(v -> v.categoryValue.equals(category))
				.findAny()
				.orElse(UNKNOWN)
				.getSelectValue();
	}
}
