package org.example.merong.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements ResponseCode {

    NOT_OWNED_RESOURCE(false, HttpStatus.FORBIDDEN, "자신이 작성한 리소스만 수정할 수 있습니다."),
    WRONG_PASSWORD(false, HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    NOT_OWNED_EMAIL(false, HttpStatus.FORBIDDEN, "현재 로그인한 계정의 이메일을 입력하십시오."),
    USER_NOT_FOUND(false, HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ALREADY_EXISTS_EMAIL(false, HttpStatus.CONFLICT, "이미 존재하는 이메일이 있습니다.");

    private final boolean isSuccess;
    private final HttpStatus status;
    private final String message;
}
