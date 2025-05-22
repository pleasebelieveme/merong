package org.example.merong.domain.searches.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.dto.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.repository.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public Page<SearchResponseDto> findPopular(Pageable pageable) {

        Page<Search> keywords = searchRepository.findAll(pageable);

        Page<SearchResponseDto> popular = keywords.map(keyword -> new SearchResponseDto(keyword.getKeyword()));

        return popular;
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
}
