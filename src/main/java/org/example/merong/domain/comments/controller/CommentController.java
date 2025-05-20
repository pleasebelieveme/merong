package org.example.merong.domain.comments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.request.CommentRequestDto.Add;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto.Update;
import org.example.merong.domain.comments.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/songs/{songId}/comments")
    public ResponseEntity<CommentResponseDto.Add> createComment(
            @PathVariable Long songId,
            @Valid @RequestBody CommentRequestDto.Add requestDto,
            @AuthenticationPrincipal UserAuth userDetails
    ){

        CommentResponseDto.Add saveComment = commentService.saveComment(songId, requestDto, userDetails.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(saveComment);
    }

    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDto.Update> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto.Update requestDto,
            @AuthenticationPrincipal UserAuth userDetails
    ){
        CommentResponseDto.Update updateComment = commentService.updateComment(commentId, requestDto, userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(updateComment);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserAuth userDetails
    ){
        commentService.deleteComment(commentId, userDetails.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글이 삭제되었습니다.");
    }

}
