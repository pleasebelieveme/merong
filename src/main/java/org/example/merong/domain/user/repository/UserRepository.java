package org.example.merong.domain.user.repository;

import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.exception.UserException;
import org.example.merong.domain.user.exception.UserExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndIsDeleted(String email, boolean isDeleted);

    Optional<User> findByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<User> findById(Long userId);

    default User findByIdOrElseThrow(Long userId){
        return findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

    }
}
