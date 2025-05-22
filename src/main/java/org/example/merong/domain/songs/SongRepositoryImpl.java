package org.example.merong.domain.songs;

import java.util.List;

import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.entity.QSong;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<SongResponseDto.Get> findByDynamicQuery(String title, String singer, Pageable pageable) {
		QSong song = QSong.song;

		BooleanBuilder builder = new BooleanBuilder();

		if (title != null && !title.isBlank()) {
			builder.and(song.title.containsIgnoreCase(title));
		}

		if (singer != null && !singer.isBlank()) {
			builder.and(song.singer.containsIgnoreCase(singer));
		}

		// 정렬 조건 설정
		List<OrderSpecifier<Comparable>> orderSpecifiers = pageable.getSort().stream()
			.map(order -> {
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;
				PathBuilder<Song> pathBuilder = new PathBuilder<>(Song.class, "song");
				return new OrderSpecifier<>(
					direction,
					pathBuilder.getComparable(order.getProperty(), Comparable.class)
				);
			})
			.toList();

		// 실제 데이터 조회 (페이징 적용)
		List<Song> results = queryFactory
			.selectFrom(song)
			.where(builder)
			.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 총 개수 조회
		long total = queryFactory
			.select(song.count())
			.from(song)
			.where(builder)
			.fetchOne();

		List<SongResponseDto.Get> dtoList = results.stream()
			.map(SongResponseDto.Get::fromEntity)
			.toList();

		return new PageImpl<>(dtoList, pageable, total);
	}

}