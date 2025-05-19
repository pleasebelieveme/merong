package org.example.merong.domain.likes.repository;

import java.util.Optional;

import org.example.merong.domain.likes.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LikeRepository extends JpaRepository<Like, Long>  {
	boolean existsByUserIdAndSongId(Long userId, Long songId);
	Optional<Like> findByUserIdAndSongId(Long userId, Long songId);
}
