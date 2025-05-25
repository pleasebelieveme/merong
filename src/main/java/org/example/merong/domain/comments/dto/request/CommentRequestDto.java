package org.example.merong.domain.comments.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Add{

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

    }



}
