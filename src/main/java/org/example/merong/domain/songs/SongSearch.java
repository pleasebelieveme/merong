package org.example.merong.domain.songs;

import org.example.merong.domain.songs.dto.request.SongSearchRequestParamDto;

import org.example.merong.domain.songs.entity.Song;
import org.springframework.data.domain.Page;

public interface SongSearch {
    Page<Song> searchLikeKeyword(SongSearchRequestParamDto paramDto);


}
