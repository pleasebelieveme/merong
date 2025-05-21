package org.example.merong.domain.searches.repository;

import org.example.merong.domain.searches.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryCustom {
}
