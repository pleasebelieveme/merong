package org.example.merong.domain.reply.repository;

import java.util.Optional;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.exception.CommentException;
import org.example.merong.domain.comments.exception.CommentExceptionCode;
import org.example.merong.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findById(Long replyId);

    default Reply findByIdOrElseThrow(Long replyId){
        return findById(replyId)
                .orElseThrow(()->
                        new CommentException(CommentExceptionCode.COMMENT_NOT_FOUND)
                );
    }
}
