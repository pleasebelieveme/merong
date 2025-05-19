package org.example.merong.domain.comments.repository;

import java.util.Optional;
import org.example.merong.domain.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long commentId,Long userId);

    default Comment findByIdAndUserIdOrElseThrow(Long commentId,Long userId){
        return findByIdAndUserId(commentId, userId)
                .orElseThrow(()->
                        new IllegalArgumentException("존재하지 않는 댓글입니다.")
                );
    }
}
