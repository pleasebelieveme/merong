package org.example.merong.domain.searches;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.dto.response.SearchResponseDto;
import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs/search")
public class SearchController {

    /**
     * 1. 통함 검색
     * @param type
     * @param keyword
     * @param orderBy
     * @param pageable
     * @return 검색 결과 반환 및 로그 저장
     */
    @PostMapping
    public ResponseEntity<Page<SearchResponseDto>> search(@RequestParam Type type,
                                                          @RequestParam String keyword,
                                                          @RequestParam Order orderBy,
                                                          @PageableDefault Pageable pageable) {

        return null;
    }
}
