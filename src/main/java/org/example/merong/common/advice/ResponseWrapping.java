package org.example.merong.common.advice;

import lombok.NonNull;
import org.example.merong.common.annotaion.ResponseMessage;
import org.example.merong.common.dto.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// 모든 REST API 응답을 공통 포맷(CommonResponse)으로 감싸는 로직
// : @ResponseMessage 어노테이션과 함께 사용해 응답 메시지를 커스터마이징
@RestControllerAdvice
public class ResponseWrapping implements ResponseBodyAdvice<Object> {

    // 어떤 컨트롤러 메서드의 응답을 가로챌지 판단
    // : 일반적인 REST API 컨트롤러만 응답 래핑 대상
    @Override
    public boolean supports(MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> declaringClass = returnType.getDeclaringClass();
        return declaringClass.isAnnotationPresent(RestController.class);
    }

    // 실제 응답 본문을 처리
    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {

        String path = request.getURI().getPath();

        if (body instanceof CommonResponse<?>) {
            return body;
        }

        ResponseMessage rm = returnType.getMethodAnnotation(ResponseMessage.class);
        String message = (rm != null)
                ? rm.value()
                : "정상적으로 수행되었습니다.";

        HttpStatusCode status = response instanceof ServletServerHttpResponse servlet
                ? HttpStatusCode.valueOf(servlet.getServletResponse().getStatus())
                : HttpStatusCode.valueOf(HttpStatus.OK.value());

        return CommonResponse.of(true, message, status.value(), body);
    }
}
