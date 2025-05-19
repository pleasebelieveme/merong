package org.example.merong.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.user.dto.request.UserDeleteRequest;
import org.example.merong.domain.user.dto.request.UserModifyRequest;
import org.example.merong.domain.user.dto.request.UserSaveRequest;
import org.example.merong.domain.user.dto.response.UserResponse;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.event.UserWithDrawEvent;
import org.example.merong.domain.user.exception.UserException;
import org.example.merong.domain.user.exception.UserExceptionCode;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    /**
     * 회원 가입<br>
     * 유저 정보를 입력 받은 후 이메일 중복검증 후 저장 진행
     * @param request 이메일, 패스워드, 이름
     * @author 지송이
     */
    @Transactional
    public void createUser(UserSaveRequest request) {

        checkEmail(request.email());

        userRepository.save(
                User.builder()
                        .email(request.email())
                        .name(request.name())
                        .password(passwordEncoder.encode(request.password()))
                        .build()
        );
    }

    /**
     * 회원 탈퇴<br>
     * 이메일, 비밀번호를 검증받고 회원 탈퇴 진행<br>
     * Soft Delete 방식<br>
     * 탈퇴 후, 해당 계정의 토큰은 블랙리스트 처리
     * @param request 검증에 필요한 이메일, 비밀번호
     * @param userId 토큰에 저장된 userId
     * @param accessToken 액세스 토큰
     * @author 지송이
     */
    @Transactional
    @PreAuthorize("@userz.checkUserId(authentication.principal.id, #request.email())")
    public void withdrawUser(UserDeleteRequest request, Long userId, String accessToken) {

        User user = userRepository.findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        user.withdraw();
        publisher.publishEvent(new UserWithDrawEvent(accessToken));
    }

    /**
     * 회원 수정<br>
     * 이메일, 비밀번호를 검증받고 회원 수정 진행<br>
     * 소셜 가입 유저는 수정불가<br>
     * @param request 검증에 필요한 이메일, 비밀번호, 신규 이름, 신규 패스워드
     * @author 지송이
     */
    @Transactional
    @PreAuthorize("hasAnyRole('owner', 'admin', 'user') and @userz.checkUserId(authentication.principal.id, #request.email())")
    public void modifyUser(UserModifyRequest request) {

        User user = userRepository.findByEmailAndPlatformAndIsDeleted(
                        request.email(), false
                )
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        checkPassword(request.password(), user.getPassword());

        user.changeProfileInformation(
                request.name(),
                passwordEncoder.encode(request.newPassword())
        );
    }

    /**
     * 회원 조회<br>
     * 현재 접속중인 계정의 정보 조회
     * @param userAuth 조회에 필요한 계정 ID
     * @return 이름, 생성일자
     * @author 지송이
     */
    @Transactional(readOnly = true)
    public UserResponse viewUser(UserAuth userAuth) {
        User user = userRepository.findById(userAuth.getId())
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }


    private void checkPassword(String rawPassword, String hashedPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw new UserException(UserExceptionCode.WRONG_PASSWORD);
        }
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmailAndPlatform(email)) {
            throw new UserException(UserExceptionCode.ALREADY_EXISTS_EMAIL);
        }
    }
}
