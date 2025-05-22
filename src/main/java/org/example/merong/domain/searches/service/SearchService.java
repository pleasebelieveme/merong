package org.example.merong.domain.searches.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.SearchRepository;
import org.example.merong.domain.searches.dto.response.SearchPopularDto;
import org.example.merong.domain.searches.dto.response.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.example.merong.domain.songs.repository.SongRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final SongRepository songRepository;
    private final CacheManager cacheManager;

    private final SearchCacheService searchCacheService;

    // 1. 통합검색 및 로그 저장
    @Transactional
    public Page<SearchResponseDto> search(Type type, String keyword, Order order, Pageable pageable) {

        logUpdate(keyword);

        return songRepository.search(type, keyword, order, pageable).map(SearchResponseDto::new);
    }

    // 1-2. 캐시를 이용한 검색 및 로그 저장
    @Transactional
    public Page<SearchResponseDto> searchV2(Type type, String keyword, Order order, Pageable pageable) {

        logUpdate(keyword);

        return getSearchResults(type, keyword, order, pageable);
    }

    // 2. 인기 검색어 조회
    public Page<SearchPopularDto> searchPopular(Pageable pageable) {

        return searchPopularPage(pageable);
    }

    // 캐시 확인
    public void printCache() {
        var cacheNames = cacheManager.getCacheNames();

        if (cacheNames.isEmpty()) {
            System.out.println("등록된 캐시가 없습니다.");
            return;
        }

        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                System.out.println();
                System.out.println("Cache 이름 : " + cache.getName());
                var nativeCache = cache.getNativeCache();
                System.out.println("Native Cache : " + nativeCache);
                System.out.println();
            } else {
                System.out.println(cacheName + " 캐시가 존재하지 않습니다.");
            }
        }
    }

    // 로그 업데이트
    public void logUpdate(String keyword) {
        // 기존에 검색 여부가 있는지 확인하고 DB에 저장 혹은 count 증가
        Search search = searchRepository.findByKeyword(keyword).orElse(null);

        if(search != null) {
            search.update();
            searchRepository.save(search);
        } else {
            searchRepository.save(new Search(keyword));
        }
    }


    /*
    직렬화 문제 해결을 위해
    CacheService 에 List<> 로 반환하는 캐시 메서드를 분리
     */

    // 인기 검색어 페이지로 반환
    public Page<SearchPopularDto> searchPopularPage(Pageable pageable) {

        List<SearchPopularDto> cachedList = searchCacheService.searchPopularList(pageable);
        long totalCount = cachedList.size();

        return new PageImpl<>(cachedList, pageable, totalCount);
    }

    // 통합 검색 결과 페이지 반환
    public Page<SearchResponseDto> getSearchResults(Type type, String keyword, Order order, Pageable pageable) {

        List<SearchResponseDto> cachedList = searchCacheService.searchList(type, keyword, order, pageable);
        long totalCount = cachedList.size();

        return new PageImpl<>(cachedList, pageable, totalCount);

    }
}
