package org.example.merong.domain.songs.entity;

import java.util.List;

import org.example.merong.common.BaseEntity;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.enums.Genres;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "songs")
public class Song extends BaseEntity {

	/**
	 * PK
	 * 노래제목
	 * 아티스트
	 * 장르
	 * 좋아요 수
	 * 재생 수
	 * 유저 FK
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String duration;

	@Column(nullable = false)
	private String singer;

	@Column(nullable = false)
	private Genres genre;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Long likeCount;

	@Column(nullable = false)
	private Long playCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	public Song(SongRequestDto dto) {
		this.user = dto.getUser();
		this.name = dto.getName();
		this.singer = dto.getSinger();
		this.genre = dto.getGenre();
		this.description = dto.getDescription();
	}

	// PATCH 요청 시
	public void updateSong(SongUpdateDto dto) {
		this.name = dto.getName();
		this.singer = dto.getSinger();
		this.genre = dto.getGenre();
		this.description = dto.getDescription();
	}
}
