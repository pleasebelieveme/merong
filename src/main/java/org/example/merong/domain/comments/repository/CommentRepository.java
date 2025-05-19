package org.example.merong.domain.comments.repository;

import java.util.Optional;
import org.example.merong.domain.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long commentId);

    default Comment findByIdOrElseThrow(Long commentId){
        return findById(commentId)
                .orElseThrow(()->
                        new IllegalArgumentException("존재하지 않는 댓글입니다.")
                );
    }
}
