package org.example.merong.domain.comments.exception;

import lombok.Getter;
import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public class CommentException extends BaseException {

    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    public CommentException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.httpStatus = responseCode.getStatus();
    }

}
