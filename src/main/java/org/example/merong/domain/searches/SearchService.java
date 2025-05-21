package org.example.merong.domain.searches;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.dto.response.SearchPopularDto;
import org.example.merong.domain.searches.dto.response.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.example.merong.domain.songs.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final SongRepository songRepository;

    // 1. 통합검색 및 로그 저장
    public Page<SearchResponseDto> search(Type type, String keyword, Order order, Pageable pageable) {

        // 기존에 검색 여부가 있는지 확인하고 DB에 저장 혹은 count 증가
        Search search = searchRepository.findByKeyword(keyword).orElse(null);

        if(search != null) {
            search.update();
            searchRepository.save(search);
        } else {
            searchRepository.save(new Search(keyword));
        }

        return songRepository.search(type, keyword, order, pageable).map(SearchResponseDto::new);
    }

    // 2. 인기 검색어 조회
    public Page<SearchPopularDto> searchPopular(Pageable pageable) {

        Page<Search> list = searchRepository.findAllByOrderByCountDesc(pageable);

        AtomicInteger atomicInteger = new AtomicInteger(1);

        return list.map(search -> new SearchPopularDto(atomicInteger.getAndIncrement(), search.getKeyword()));
    }

}
