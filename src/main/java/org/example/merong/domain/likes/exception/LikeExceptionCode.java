package org.example.merong.domain.likes.exception;

import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeExceptionCode implements ResponseCode {

    LIKE_NOT_FOUND(false, HttpStatus.NOT_FOUND, "좋아요가 없습니다.");

    private final boolean isSuccess;
    private final HttpStatus status;
    private final String message;
}
