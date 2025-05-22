package org.example.merong.domain.searches.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private LocalDateTime searched_at;

	@Column(nullable = false)
	private int count = 0;

	public Search(String keyword){
		this.keyword =keyword;
		this.searched_at = LocalDateTime.now();
	}

	public void updateCount(){
		this.count = this.count + 1;
	}

}
