package org.example.merong.domain.searches.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.merong.domain.searches.dto.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.repository.SearchRepository;
import org.example.merong.domain.songs.repository.SongSearch;
import org.example.merong.domain.songs.dto.request.SongSearchRequestParamDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto.find;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String SEARCH_RANK_KEY = "search:rank";

    private final SearchRepository searchRepository;

    private final SongSearch songSearch;

    public Page<SearchResponseDto> findPopular(Pageable pageable) {

        Page<Search> keywords = searchRepository.findAll(pageable);

        Page<SearchResponseDto> popular = keywords.map(keyword -> new SearchResponseDto(keyword.getKeyword()));

        return popular;
    }

    @Cacheable(value = "songSearchCache", key = "#paramDto.toCacheKey()")
    public Page<SongResponseDto.find> getSearchResult(SongSearchRequestParamDto paramDto) {

        paramDto.setPage(paramDto.getPage());
        paramDto.setSize(paramDto.getSize());
        paramDto.setSort(paramDto.getSort());
        paramDto.setDirection(paramDto.getDirection());

        Page<Song> songs = songSearch.searchLikeKeyword(paramDto);

        return songs.map(song -> new find(
                        song.getTitle(),
                        song.getSinger(),
                        song.getGenre(),
                        song.getCreatedAt(),
                        song.getLikeCount(),
                        song.getPlayCount(),
                        song.getDescription()
                )
        );
    }


    @Transactional
    public void saveKeyword(String keyword) {
        if(searchRepository.existsByKeyword(keyword)) {
            Search findKeyword = searchRepository.findByKeyword(keyword);
            findKeyword.updateCount();
        } else {
            Search search = new Search(keyword);
            searchRepository.save(search);
        }
    }

    // 인기검색어 검색 횟수 증가
    public void addSearchKeywordRank(String keyword){
        Double v = redisTemplate.opsForZSet().incrementScore(SEARCH_RANK_KEY, keyword, 1);

        log.info("{}, {}", keyword, v);

    }

    // 인기검색어 조회
    public Set<String> getTopSearchKeywords(){
        return redisTemplate.opsForZSet().reverseRange(SEARCH_RANK_KEY, 0 ,9);
    }


}
