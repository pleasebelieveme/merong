package org.example.merong.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.merong.domain.user.entity.User;

@Builder
@Getter
public class UserAuth {

    private Long id;
    private String email;

    public static UserAuth from(User user) {
        return UserAuth.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
