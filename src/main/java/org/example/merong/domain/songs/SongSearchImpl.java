package org.example.merong.domain.songs;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.merong.domain.songs.dto.request.SongSearchRequestParamDto;
import org.example.merong.domain.songs.entity.QSong;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SongSearchImpl extends QuerydslRepositorySupport implements SongSearch {

    public SongSearchImpl(JPAQueryFactory jpaQueryFactory) {
        super(Song.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

//    public SongSearchImpl(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
//        super(domainClass);
//        this.jpaQueryFactory = jpaQueryFactory;
//    }


    @Override
    public Page<Song> searchLikeKeyword(SongSearchRequestParamDto paramDto) {

        QSong song = QSong.song;

        log.info("size={}, page={} title={} singer={} genre={} direction={} sort={}" , paramDto.getSize(), paramDto.getPage(), paramDto.getTitle(),paramDto.getSinger(), paramDto.getGenre(), paramDto.getDirection(), paramDto.getSort());


        Pageable pageable = PageRequest.of(paramDto.getPage(), paramDto.getSize());

        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(paramDto);

        BooleanBuilder predicate = new BooleanBuilder();

        if(paramDto.getTitle() != null && !paramDto.getTitle().isBlank()){
            predicate.and(song.title.containsIgnoreCase(paramDto.getTitle()));
        }

        if(paramDto.getSinger() != null && !paramDto.getSinger().isBlank()){
            predicate.and(song.singer.containsIgnoreCase(paramDto.getSinger()));
        }

        if(paramDto.getGenre() != null){
            predicate.and(song.genre.eq(paramDto.getGenre()));
        }

//        List<Song> paging = jpaQueryFactory
//                .select(new QSongResponseDto_Search(
//                        song.title,
//                        song.singer,
//                        song.genre,
//                        song.createdAt,
//                        song.likeCount,
//                        song.playCount,
//                        song.description))
//                .from(song)
//                .where(predicate)
//                .orderBy(orderSpecifiers)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();

        List<Song> paging = jpaQueryFactory
                .select(song)
                .from(song)
                .where(predicate)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(song)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(paging, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(SongSearchRequestParamDto paramDto){
        PathBuilder<Song> pathBuilder = new PathBuilder<>(Song.class, "song");

        String sortField = paramDto.getSort();

        Order order = paramDto.getDirection().isAscending() ? Order.ASC : Order.DESC;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, pathBuilder.get(sortField));

        return new OrderSpecifier[] {orderSpecifier};


    }
}
