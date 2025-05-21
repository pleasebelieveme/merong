package org.example.merong.domain.songs.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.entity.Song;
//import org.example.merong.domain.songs.entity.QSong;


import java.util.List;

@RequiredArgsConstructor
public class SongQueryRepositoryImpl implements SongQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Song> searchByKeyword(String keyword) {
//        QSong song = QSong.song;
//        return queryFactory
//                .selectFrom(song)
//                .where(song.title.containsIgnoreCase(keyword)
//                        .or(song.singer.containsIgnoreCase(keyword)))
//                .fetch();
        return null;
    }
}
