package org.example.merong.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.merong.common.annotaion.ResponseMessage;
import org.example.merong.common.utils.SecurityUtils;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.auth.dto.request.LoginRequest;
import org.example.merong.domain.auth.dto.response.TokenResponse;
import org.example.merong.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseMessage("정상적으로 로그인이 되었습니다.")
    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Validated LoginRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @ResponseMessage("정상적으로 로그아웃 되었습니다.")
    @PostMapping("/signout")
    public ResponseEntity<Void> signOut() {
        authService.signOut(SecurityUtils.getCurrentToken());
        SecurityUtils.clearContext();
        return ResponseEntity.ok().build();
    }

    @ResponseMessage("정상적으로 토큰이 재발급되었습니다.")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal UserAuth userAuth) {
        return ResponseEntity.ok(authService.reissue(userAuth, SecurityUtils.getCurrentToken()));
    }
}

