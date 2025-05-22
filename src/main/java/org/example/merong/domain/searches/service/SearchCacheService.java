package org.example.merong.domain.searches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.merong.domain.searches.SearchRepository;
import org.example.merong.domain.searches.dto.response.SearchPopularDto;
import org.example.merong.domain.searches.dto.response.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.repository.SongRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchCacheService {

    private final SongRepository songRepository;
    private final SearchRepository searchRepository;

    @Cacheable(
            value = "searchCache",
            key = "#type.name() + ':' + #keyword + ':' + #order.name() + ':' + #pageable.pageNumber + ':' + #pageable.pageSize"
    )
    public List<SearchResponseDto> searchList(Type type, String keyword, Order order, Pageable pageable) {

        Page<Song> page = songRepository.search(type, keyword, order, pageable);

        return page.stream()
                .map(SearchResponseDto::new)
                .toList();
    }

    // 캐싱 및 인기 검색어 리스트 반환
    @Cacheable(value = "popularCache")
    public List<SearchPopularDto> searchPopularList(Pageable pageable) {

        Page<Search> page = searchRepository.findAllByOrderByCountDesc(pageable);
        AtomicInteger atomicInteger = new AtomicInteger(1);

        return page.stream()
                .map(search -> new SearchPopularDto(atomicInteger.getAndIncrement(), search.getKeyword()))
                .toList();
    }



}
