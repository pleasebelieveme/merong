package org.example.merong.jwt.exception;

import lombok.Getter;
import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public class TokenException extends BaseException {

    private final ResponseCode responseCode;
    private final String message;
    private final HttpStatus httpStatus;

    public TokenException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = responseCode.getMessage();
        this.httpStatus = responseCode.getStatus();
    }
}
