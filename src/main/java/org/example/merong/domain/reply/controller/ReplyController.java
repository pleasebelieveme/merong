package org.example.merong.domain.reply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.reply.dto.request.ReplyRequestDto;
import org.example.merong.domain.reply.dto.response.ReplyResponseDto;
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
            @AuthenticationPrincipal UserAuth userAuth
    ){

        ReplyResponseDto.Add saveReply = replyService.saveReply(commentId, requestDto, userAuth.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(saveReply);
    }

    @PatchMapping("/api/replies/{replyId}")
    public ResponseEntity<ReplyResponseDto.Update> updateReply(
            @PathVariable Long replyId,
            @Valid @RequestBody ReplyRequestDto.Update requestDto,
            @AuthenticationPrincipal UserAuth userAuth
    ){
        ReplyResponseDto.Update updateReply = replyService.updateReply(replyId, requestDto, userAuth.getId());

        return ResponseEntity.status(HttpStatus.OK).body(updateReply);
    }

    @DeleteMapping("/api/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserAuth userAuth
    ){
        replyService.deleteReply(replyId, userAuth.getId());

        return ResponseEntity.noContent().build();
    }


}
