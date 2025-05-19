package org.example.merong.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.auth.dto.request.LoginRequest;
import org.example.merong.domain.auth.dto.response.TokenResponse;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.exception.UserException;
import org.example.merong.domain.user.exception.UserExceptionCode;
import org.example.merong.domain.user.repository.UserRepository;
import org.example.merong.jwt.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * 회원 로그인<br>
     * 유저 정보를 검증 한 후, 액세스 토큰, 리프레시 토큰 발급
     * @param request 이메일, 비밀번호
     * @return 액세스 토큰, 리프레시 토큰
     * @author 지송이
     */
    @Transactional
    public TokenResponse signIn(LoginRequest request) {

        User user = userRepository.findByEmailAndIsDeleted(request.email(), false)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        checkPassword(request.password(), user.getPassword());

        return jwtService.generateToken(UserAuth.from(user), new Date());
    }


    public void signOut(String accessToken) {

        jwtService.addBlackListToken(accessToken);
    }

    public TokenResponse reissue(UserAuth userAuth, String refreshToken) {

        return jwtService.reissueToken(userAuth, refreshToken);
    }

    private void checkPassword(String rawPassword, String hashedPassword) {

        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw new UserException(UserExceptionCode.WRONG_PASSWORD);
        }

    }

}

