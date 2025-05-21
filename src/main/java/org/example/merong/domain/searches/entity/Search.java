package org.example.merong.domain.searches.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "searches")
public class Search {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String keyword;

	@Column(nullable = false)
	private LocalDateTime searchedAt;

	@Column(nullable = false)
	private Long count;

	public Search(String keyword) {
		this.keyword = keyword;
		this.searchedAt = LocalDateTime.now();
		this.count = 0L;
	}

	public void update() {
		this.count++;
		this.searchedAt = LocalDateTime.now();
	}
}
