package org.example.merong.domain.reply.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add{

        private Long userId;

        private String content;

        private LocalDateTime createdAt;

        private Long commentId;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{

        private Long userId;

        private String content;

        private LocalDateTime updatedAt;

        private Long commentId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get{

        private Long userId;

        private String content;

        private LocalDateTime updatedAt;
    }

}
