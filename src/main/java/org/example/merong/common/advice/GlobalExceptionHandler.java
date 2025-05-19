package org.example.merong.common.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.merong.common.dto.CommonResponse;
import org.example.merong.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // 유효성 검증 실패한 경우
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        log.error("Validation failed: {}", e.getMessage());

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");

        return ResponseEntity
                .badRequest()
                .body(CommonResponse.of(false, errorMessage, 400, null));
    }

    // 개발자가 직접 정의한 BaseException 을 throw 할 경우
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CommonResponse<Void>> handleBaseException(
            BaseException e
    ) {
        return ResponseEntity.status(e.getHttpStatus()).body(CommonResponse.from(e.getResponseCode()));
    }

    // 인증은 되었지만 권한이 부족할 경우
    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<CommonResponse<Void>> handleAccessDeniedException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        CommonResponse.of(
                                false,
                                "접근권한이 없습니다.",
                                HttpServletResponse.SC_FORBIDDEN,
                                null
                        )
                );
    }
}
