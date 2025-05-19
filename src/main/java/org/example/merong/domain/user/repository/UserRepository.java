package org.example.merong.domain.user.repository;

import org.example.merong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndIsDeleted(String email, boolean isDeleted);

    Optional<User> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
