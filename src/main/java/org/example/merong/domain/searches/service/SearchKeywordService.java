package org.example.merong.domain.searches.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.repository.SearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchRepository searchRepository;

    @Transactional
    public void saveKeyword(String keyword) {
        if(searchRepository.existsByKeyword(keyword)) {
            Search findKeyword = searchRepository.findByKeyword(keyword);
            findKeyword.updateCount();
        } else {
            Search search = new Search(keyword);
            search.updateCount();
            searchRepository.save(search);
        }
    }
}
