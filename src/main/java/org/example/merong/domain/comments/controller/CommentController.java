package org.example.merong.domain.comments.controller;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.comments.dto.request.CommentRequestDto.Add;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs/{songId}")
public class CommentController {

//    private final CommentService commentService;
//
//    @PostMapping("/comments")
//    public ResponseEntity<CommentResponseDto.Add> createComment(
//            @PathVariable Long songId,
//            @RequestBody Add requestDto,
//            @AuthenticationPrincipal CustomUserDetails userDetails
//    ){
//        CommentResponseDto.Add comment = commentService.saveComment(songId, requestDto, userDetails.getUserId());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
//    }

}
