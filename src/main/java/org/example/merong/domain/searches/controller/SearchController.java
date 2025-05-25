package org.example.merong.domain.searches.controller;


import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.dto.SearchResponseDto;
import org.example.merong.domain.searches.repository.SearchRepository;
import org.example.merong.domain.searches.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/songs/search/popular")
    public ResponseEntity<Page<SearchResponseDto>> findPopular(
            @PageableDefault(size = 10, sort = "count", direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<SearchResponseDto> popular = searchService.findPopular(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(popular);
    }

    @GetMapping("/api/songs/search/popular/v2")
    public ResponseEntity<Set<String>> findPopularV2(
            @PageableDefault(size = 10, sort = "count", direction = Sort.Direction.DESC) Pageable pageable
    ){

        Set<String> popular = searchService.getTopSearchKeywords();

        return ResponseEntity.status(HttpStatus.OK).body(popular);
    }

}
