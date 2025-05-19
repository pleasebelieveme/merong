package org.example.merong.domain.comments.repository;

import java.util.Optional;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.exception.CommentException;
import org.example.merong.domain.comments.exception.CommentExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long commentId);

    default Comment findByIdOrElseThrow(Long commentId){
        return findById(commentId)
                .orElseThrow(()->
                        new CommentException(CommentExceptionCode.COMMENT_NOT_FOUND)
                );
    }
}
