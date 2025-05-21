package org.example.merong.domain.songs.repository;

import org.example.merong.domain.songs.entity.Song;

import java.util.List;

public interface SongQueryRepository {
    List<Song> searchByKeyword(String keyword);
}
