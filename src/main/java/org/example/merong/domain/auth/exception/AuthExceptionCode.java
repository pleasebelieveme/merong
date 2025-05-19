package org.example.merong.domain.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ResponseCode {

    UNSUPPORTED_OAUTH_PROVIDER(false, HttpStatus.BAD_REQUEST, "지원하지 않는 인가 서버입니다.");

    private final boolean isSuccess;
    private final HttpStatus status;
    private final String message;
}
