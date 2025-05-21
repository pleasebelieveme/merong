package org.example.merong.domain.songs;

import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.exception.SongException;
import org.example.merong.domain.songs.exception.SongsExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

    Optional<Song> findById(Long songId);

    default Song findByIdOrElseThrow(Long songId) {
        return findById(songId).orElseThrow(() -> new SongException(SongsExceptionCode.SONG_NOT_FOUND));
    }
}
