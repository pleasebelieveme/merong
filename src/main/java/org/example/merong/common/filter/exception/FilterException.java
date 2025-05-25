package org.example.merong.common.filter.exception;

import lombok.Getter;
import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public class FilterException extends BaseException {
    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    public FilterException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.httpStatus = responseCode.getStatus();
    }
}
