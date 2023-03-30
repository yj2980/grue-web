package web.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Board extends Time{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ColumnDefault("0")
	private int views;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userID;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(length = 5000)
	private String content;

	@Column(nullable = false, length = 100)
	private String category;

}
