package org.example.merong.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteRequest(

        @NotBlank
        String email,

        String password

) {
    public UserDeleteRequest {
        if(password == null) {
            password = "default";
        }
    }
}
