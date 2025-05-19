package org.example.merong.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.merong.common.exception.ResponseCode;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(
        boolean success,
        int status,
        String message,
        T result
) {
    public static <T> CommonResponse<T> of(boolean success, String message, int status, T result) {
        return CommonResponse.<T>builder()
                .success(success)
                .message(message)
                .status(status)
                .result(result)
                .build();
    }

    public static <T> CommonResponse<T> from(ResponseCode responseCode) {
        return CommonResponse.<T>builder()
                .success(responseCode.isSuccess())
                .status(responseCode.getStatus().value())
                .message(responseCode.getMessage())
                .build();
    }
}
