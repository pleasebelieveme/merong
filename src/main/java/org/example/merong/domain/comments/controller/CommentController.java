package org.example.merong.domain.comments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.dto.CommonResponse;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/songs/{songId}/comments")
    public ResponseEntity<CommentResponseDto.Add> createComment(
            @PathVariable Long songId,
            @Valid @RequestBody CommentRequestDto.Add requestDto,
            @AuthenticationPrincipal UserAuth userAuth
    ){

        CommentResponseDto.Add saveComment = commentService.saveComment(songId, requestDto, userAuth.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(saveComment);
    }

    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDto.Update> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto.Update requestDto,
            @AuthenticationPrincipal UserAuth userAuth
    ){
        CommentResponseDto.Update updateComment = commentService.updateComment(commentId, requestDto, userAuth.getId());

        return ResponseEntity.status(HttpStatus.OK).body(updateComment);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserAuth userAuth
    ){
        commentService.deleteComment(commentId, userAuth.getId());


        return ResponseEntity.noContent().build();
    }

}
