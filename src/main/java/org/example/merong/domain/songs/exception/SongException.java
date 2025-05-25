package org.example.merong.domain.songs.exception;

import lombok.Getter;
import org.example.merong.common.exception.BaseException;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public class SongException extends BaseException {

    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    public SongException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.httpStatus = responseCode.getStatus();
    }
}
