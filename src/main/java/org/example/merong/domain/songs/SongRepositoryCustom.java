package org.example.merong.domain.songs;

import java.util.List;

import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepositoryCustom {
	Page<SongResponseDto.Get> findByDynamicQuery(String title, String artist, Pageable pageable);
}