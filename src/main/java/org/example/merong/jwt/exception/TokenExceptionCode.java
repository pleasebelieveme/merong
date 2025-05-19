package org.example.merong.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenExceptionCode implements ResponseCode {

    REFRESH_TOKEN_EXPIRED(false, HttpStatus.FORBIDDEN, "리프레쉬 토큰이 만료되었습니다."),
    NOT_VALID_JWT_TOKEN(false, HttpStatus.FORBIDDEN, "올바르지 않은 JWT 토큰입니다."),
    NOT_VALID_SIGNATURE(false, HttpStatus.FORBIDDEN, "서명이 올바르지 않습니다."),
    NOT_VALID_CONTENT(false, HttpStatus.FORBIDDEN, "내용이 올바르지 않습니다.");

    private final boolean success;
    private final HttpStatus status;
    private final String message;
}
