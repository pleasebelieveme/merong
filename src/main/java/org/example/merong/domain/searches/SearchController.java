package org.example.merong.domain.searches;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.dto.response.SearchPopularDto;
import org.example.merong.domain.searches.dto.response.SearchResponseDto;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs/search")
public class SearchController {

    private final SearchService searchService;

    /**
     * 1. 통함 검색
     * @param type
     * @param keyword
     * @param order
     * @param pageable
     * @return 검색 결과 반환 및 로그 저장
     */
    @PostMapping
    public ResponseEntity<Page<SearchResponseDto>> search(@RequestParam Type type,
                                                          @RequestParam String keyword,
                                                          @RequestParam Order order,
                                                          @PageableDefault Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(searchService.search(type, keyword, order, pageable));
    }

    /**
     * 2. 인기 검색어 조회
     * @param pageable
     * @return count 많은 순서대로 검색어 10개 반환
     */
    @GetMapping("/popular")
    public ResponseEntity<Page<SearchPopularDto>> searchPopular(
            @PageableDefault(sort = "count", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchPopular(pageable));
    }
}
