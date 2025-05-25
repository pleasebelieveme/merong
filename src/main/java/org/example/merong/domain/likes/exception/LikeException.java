package org.example.merong.domain.likes.exception;

import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class LikeException extends BaseException {

    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    public LikeException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.httpStatus = responseCode.getStatus();
    }

}
