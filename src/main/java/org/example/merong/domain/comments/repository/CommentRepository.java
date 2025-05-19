package org.example.merong.domain.comments.repository;

import org.example.merong.domain.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
