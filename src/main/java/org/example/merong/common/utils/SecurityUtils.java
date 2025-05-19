package org.example.merong.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    // 현재 로그인한 사용자의 토큰 추출
    public static String getCurrentToken() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    }

    // 현재 SecurityContext(로그인 정보) 를 삭제
    public static void clearContext() {
        SecurityContextHolder.clearContext();
    }
}
