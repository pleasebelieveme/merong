package org.example.merong.domain.songs;

import java.util.List;

import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.entity.QSong;
import org.example.merong.domain.songs.entity.Song;
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
	public List<SongResponseDto.Get> findByDynamicQuery(String title, String singer, String sort, String orderBy) {
		QSong song = QSong.song;

		BooleanBuilder builder = new BooleanBuilder();

		if (title != null && !title.isBlank()) {
			builder.and(song.title.containsIgnoreCase(title));
		}

		if (singer != null && !singer.isBlank()) {
			builder.and(song.singer.containsIgnoreCase(singer));
		}

		// 정렬 방향 설정
		Order direction = orderBy.equalsIgnoreCase("asc") ? Order.ASC : Order.DESC;
		PathBuilder<?> entityPath = new PathBuilder<>(Song.class, "song");
		OrderSpecifier<?> orderSpecifier = new OrderSpecifier(direction, entityPath.get(sort));

		List<Song> results = queryFactory
			.selectFrom(song)
			.where(builder)
			.orderBy(orderSpecifier)
			.fetch();

		// Dto 변환
		return results.stream()
			.map(SongResponseDto.Get::fromEntity)
			.toList();
	}
}