package org.example.merong.domain.searches.repository;

import org.example.merong.domain.searches.dto.SearchResponseDto;
import org.example.merong.domain.searches.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    boolean existsByKeyword(String keyword);

//    Optional<Search> findByKeyword(String keyword);

    Search findByKeyword(String Keyword);

//    Page<Search> findKeyword(Pageable pageable);
}
