package org.example.merong.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.merong.domain.user.entity.User;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(

        String email,

        String name,

        LocalDateTime createdAt

) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .build();
    }
}

