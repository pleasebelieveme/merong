package org.example.merong.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.filter.exception.FilterException;
import org.example.merong.common.filter.exception.FilterExceptionCode;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.jwt.constants.JwtConstants;
import org.example.merong.jwt.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseJwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (shouldSkip(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = getTokenFromRequest(request);
        if (authorization == null || authorization.isBlank()) {
            throw new FilterException(FilterExceptionCode.EMPTY_TOKEN);
        }

        String token = resolveToken(authorization);

        if (jwtService.isBlackListed(token) && shouldCheckBlackList()) {
            throw new FilterException(FilterExceptionCode.CANT_USE_TOKEN);
        }

        if (jwtService.isTokenExpired(token)) {
            throw new FilterException(FilterExceptionCode.TOKEN_EXPIRED);
        }

        UserAuth userAuth = jwtService.getUserAuth(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userAuth,
                token,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(String authorization) {
        if (!authorization.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new FilterException(FilterExceptionCode.INVALID_TOKEN_USAGE);
        }
        return authorization.substring(JwtConstants.TOKEN_PREFIX.length());
    }

    protected abstract boolean shouldSkip(HttpServletRequest request);

    protected abstract String getTokenFromRequest(HttpServletRequest request);

    protected abstract boolean shouldCheckBlackList();
}
