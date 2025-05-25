package org.example.merong.domain.comments.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum CommentExceptionCode implements ResponseCode {

    COMMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");

    private final boolean isSuccess;
    private final HttpStatus status;
    private final String message;
}
