package org.example.merong.common.filter.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FilterExceptionCode implements ResponseCode {

    TOKEN_EXPIRED(false, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    EMPTY_TOKEN(false, HttpStatus.UNAUTHORIZED, "헤더에 토큰을 포함하고 있지 않습니다."),
    MALFORMED_JWT_REQUEST(false, HttpStatus.UNAUTHORIZED, "요청 형태가 잘못 되었습니다."),
    CANT_USE_TOKEN(false, HttpStatus.UNAUTHORIZED, "사용할 수 없는 토큰입니다."),
    INVALID_TOKEN_USAGE(false, HttpStatus.FORBIDDEN, "잘못된 유형의 토큰입니다.");

    private final boolean success;
    private final HttpStatus status;
    private final String message;
}
