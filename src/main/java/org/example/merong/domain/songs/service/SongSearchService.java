package org.example.merong.domain.songs.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.repository.SongRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongSearchService {

    private final SongRepository songRepository;

    @Cacheable(value = "songSearchCache", key = "#keyword")
    public List<SongResponseDto.Get> getSearchResults(String keyword) {
        System.out.println("[캐시 미적용] 실제 DB 조회 수행");
        return songRepository.searchByKeyword(keyword)
                .stream()
                .map(SongResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
