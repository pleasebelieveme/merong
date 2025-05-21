package org.example.merong.domain.songs;

import java.util.List;

import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepositoryCustom {
	List<SongResponseDto.Get> findByDynamicQuery(String title, String artist, String sort, String orderBy);
}