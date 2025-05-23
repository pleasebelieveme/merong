package org.example.merong.domain.songs.entity;

import java.util.List;

import jakarta.persistence.*;
import org.example.merong.common.base.BaseEntity;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.enums.Genres;

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
	private String title;

	@Column(nullable = false)
	private String singer;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
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

	public Song(User user, SongRequestDto dto) {
		this.user = user;
		this.title = dto.getName();
		this.singer = dto.getSinger();
		this.genre = dto.getGenre();
		this.description = dto.getDescription();
		this.likeCount = 0L;
		this.playCount = 0L;
	}

	// PATCH 요청 시
	public void updateSong(SongUpdateDto dto) {
		if(dto.getName() != null) this.title = dto.getName();
		if(dto.getSinger() != null) this.singer = dto.getSinger();
		if(dto.getGenre() != null) this.genre = dto.getGenre();
		if(dto.getDescription() != null) this.description = dto.getDescription();
	}

	// 레디스에서 조회수 반영을 위한 메서드
	public void updatePlayCounts(Long cnt) {
		this.playCount = cnt;
	}
}
