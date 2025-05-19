package org.example.merong.domain.reply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.security.CustomUserDetails;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto.Add;
import org.example.merong.domain.reply.dto.request.ReplyRequestDto;
import org.example.merong.domain.reply.dto.response.ReplyResponseDto;
import org.example.merong.domain.reply.entity.Reply;
import org.example.merong.domain.reply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<ReplyResponseDto.Add> createReply(
            @PathVariable Long commentId,
            @Valid @RequestBody ReplyRequestDto.Add requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){

        ReplyResponseDto.Add saveReply = replyService.saveReply(commentId, requestDto, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(saveReply);
    }

    @PatchMapping("/api/replies/{replyId}")
    public ResponseEntity<ReplyResponseDto.Update> updateReply(
            @PathVariable Long replyId,
            @Valid @RequestBody ReplyRequestDto.Update requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        ReplyResponseDto.Update updateReply = replyService.updateReply(replyId, requestDto, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(updateReply);
    }

    @DeleteMapping("/api/replies/{replyId}")
    public ResponseEntity<String> deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        replyService.deleteReply(replyId, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("대댓글이 삭제되었습니다.");
    }


}
