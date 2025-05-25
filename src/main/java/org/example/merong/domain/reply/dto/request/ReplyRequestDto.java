package org.example.merong.domain.reply.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyRequestDto {

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
