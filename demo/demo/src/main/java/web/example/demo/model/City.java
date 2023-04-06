package web.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 10)
	private String cityName;

	@Column(nullable = false, length = 10)
	private String korName;

	@Column(nullable = false, length = 600)
	private String description;

	@ColumnDefault("0")
	private int selectNumber;

	@ColumnDefault("'none'")
	private String visa;

	@Column(nullable = false, length = 10)
	private String timeDifference;

	@Column(nullable = false)
	private String savedFilePath;
	@Column(nullable = false)
	private String storedFileName;

}
