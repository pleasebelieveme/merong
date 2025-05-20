package org.example.merong.common.security;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component("userz")
@RequiredArgsConstructor
public class UserPermissionChecker {

    private final UserRepository userRepository;

    public boolean checkUserId(Long idFromToken, String emailFromRequest) {
        return userRepository.findByEmailAndIsDeleted(emailFromRequest, false)
                .map(user -> user.getId().equals(idFromToken))
                .orElse(false);
    }

    public boolean isAdmin(Long userId) {
        return userRepository.findByIdAndIsDeleted(userId, false)
                .map(user -> user.getEmail().equals("admin@example.com"))
                .orElse(false);
    }
}
