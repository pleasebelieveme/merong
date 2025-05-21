package org.example.merong.domain.songs.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.example.merong.domain.songs.entity.QSong;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Song> search(Type type, String keyword, Order order, Pageable pageable) {

        QSong song = QSong.song;

        BooleanBuilder builder = new BooleanBuilder();

        if(type != null && keyword != null && !keyword.isEmpty()) {

            switch(type) {
                case TITLE -> builder.and(song.title.containsIgnoreCase(keyword));
                case SINGER -> builder.and(song.singer.containsIgnoreCase(keyword));
                case GENRE -> builder.and(song.genre.stringValue().containsIgnoreCase(keyword));
                case ALL -> builder.and(song.title.containsIgnoreCase(keyword))
                        .or(song.singer.containsIgnoreCase(keyword))
                        .or(song.genre.stringValue().containsIgnoreCase(keyword));
            }

        }

        JPAQuery<Song> query = queryFactory
                .select(song)
                .from(song)
                .where(builder);

        if(order != null) {
            switch(order) {
                case LATEST -> query.orderBy(song.createdAt.asc());
                case OLDEST -> query.orderBy(song.createdAt.desc());
            }
        }

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        List<Song> songs = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(songs, pageable, total);
    }
}
