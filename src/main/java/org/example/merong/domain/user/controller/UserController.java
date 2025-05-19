package org.example.merong.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.merong.common.annotaion.ResponseMessage;
import org.example.merong.common.utils.SecurityUtils;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.user.dto.request.UserDeleteRequest;
import org.example.merong.domain.user.dto.request.UserModifyRequest;
import org.example.merong.domain.user.dto.request.UserSaveRequest;
import org.example.merong.domain.user.dto.response.UserResponse;
import org.example.merong.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseMessage("정상적으로 가입처리 되었습니다.")
    public ResponseEntity<Void> createUser(
            @RequestBody @Validated UserSaveRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @ResponseMessage("정상적으로 탈퇴처리 되었습니다.")
    public ResponseEntity<Void> withdrawUser(
            @RequestBody @Validated UserDeleteRequest request,
            @AuthenticationPrincipal UserAuth userAuth
    ) {
        userService.withdrawUser(request, userAuth.getId(), SecurityUtils.getCurrentToken());
        SecurityUtils.clearContext();
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @ResponseMessage("정상적으로 수정되었습니다.")
    public ResponseEntity<Void> modifyUser(
            @RequestBody @Validated UserModifyRequest request) {
        userService.modifyUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ResponseMessage("정상적으로 조회되었습니다.")
    public ResponseEntity<UserResponse> viewUser(
            @AuthenticationPrincipal UserAuth userAuth) {
        return ResponseEntity.ok(userService.viewUser(userAuth));
    }
}
