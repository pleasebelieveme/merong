package org.example.merong.common.security;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB 에서 사용자를 찾고, CustomUserDetails 로 감싸서 반환
        User user = userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다: " + email));

        return new CustomUserDetails(user);
    }

}
