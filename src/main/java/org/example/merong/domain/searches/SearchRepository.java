package org.example.merong.domain.searches;

import org.example.merong.domain.searches.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    Optional<Search> findByKeyword(String keyword);

    Page<Search> findAllByOrderByCountDesc(Pageable pageable);
}
