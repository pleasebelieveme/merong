package org.example.merong.domain.songs.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SongsExceptionCode implements ResponseCode {

    SONG_NOT_FOUND(false, HttpStatus.NOT_FOUND, "해당 노래를 찾을 수 없습니다."),
    SONG_OWNERSHIP_EXCEPTION(false, HttpStatus.FORBIDDEN, "자신의 컨텐츠에만 접근할 수 있습니다.");

    private final boolean isSuccess;
    private final HttpStatus status;
    private final String message;

}
