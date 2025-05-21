package org.example.merong.domain.likes.service;

import org.example.merong.domain.likes.dto.request.LikeRequestDto;
import org.example.merong.domain.likes.dto.response.LikeResponseDto;
import org.example.merong.domain.likes.entity.Like;
import org.example.merong.domain.likes.exception.LikeException;
import org.example.merong.domain.likes.exception.LikeExceptionCode;
import org.example.merong.domain.likes.repository.LikeRepository;
import org.example.merong.domain.songs.SongRepository;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.user.repository.UserRepository;
import org.example.merong.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final SongRepository songRepository;

	public LikeResponseDto.Add doLike(Long songId, Long userId) {

		// 중복 좋아요 체크
		boolean exists = likeRepository.existsByUserIdAndSongId(songId, userId);
		if (exists) {
			throw new LikeException(LikeExceptionCode.LIKE_NOT_FOUND);
		}

		// 유저 존재유무
		User findUser = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		// 노래 존재유무
		Song findSong = songRepository.findByIdOrElseThrow(songId);

		Like like = Like.builder()
			.user(findUser)
			.song(findSong)
			.build();

		likeRepository.save(like);

		return new LikeResponseDto.Add(like.getId(), findUser.getId(), findSong.getId());
	}

	@Transactional
	public void unLike(Long songId, Long likeId, Long userId) {

		// 좋아요가 존재하는지 확인
		Like findLike = likeRepository.findByUserIdAndSongIdOrElseThrow(songId, userId);

		likeRepository.delete(findLike);
	}
}
