package org.example.merong.domain.auth.exception;

import lombok.Getter;
import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends BaseException {
    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    public AuthException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.httpStatus = responseCode.getStatus();
    }
}