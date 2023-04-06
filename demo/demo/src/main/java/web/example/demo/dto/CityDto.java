package web.example.demo.dto;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.example.demo.model.City;

public class CityDto {
	@Getter
	@Setter
	@ToString
	public class Create {
		String filePath =  System.getProperty("user.dir")+ "/src/main/resources/static/images/city";
		private String cityName;
		private String korName;
		private String description;
		private int selectNumber;
		private String visa;
		private String timeDifference;

		public void saveFile(String filename, MultipartFile cityImg) throws IOException {
			File saveFile = new File(filePath, filename);
			cityImg.transferTo(saveFile);
		}
		public City toBoard(MultipartFile cityImg) throws IOException {
			System.out.println("\n\ncity Name : " + cityName + "\n\n");

			String fileName = cityName + cityImg.getOriginalFilename().substring(cityImg.getOriginalFilename().lastIndexOf("."));
			saveFile(fileName, cityImg);

			return City.builder()
					.cityName(cityName)
					.korName(korName)
					.description(description)
					.selectNumber(selectNumber)
					.visa("none")
					.timeDifference(timeDifference)
					.storedFileName(cityName)
					.savedFilePath("images/city/" + cityName + fileName.substring(fileName.lastIndexOf(".")))
					.build();
		}
	}

	@Getter
	@Setter
	@Builder
	public static class ShowCityDTO {
		private String cityName;
		private String korName;
		private String imgPath;

		public static CityDto.ShowCityDTO toDTO(City entity) {
			return ShowCityDTO.builder()
					.cityName(entity.getCityName())
					.korName(entity.getKorName())
					.imgPath(entity.getSavedFilePath())
					.build();
		}
	}

}
