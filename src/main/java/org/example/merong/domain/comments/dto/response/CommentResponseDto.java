package org.example.merong.domain.comments.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add{

        private Long userId;

        private String content;

        private LocalDateTime createdAt;

        private Long songId;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{

        private Long userId;

        private String content;

        private LocalDateTime updatedAt;

        private Long songId;
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
