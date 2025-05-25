package org.example.merong.jwt.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.example.merong.common.filter.exception.FilterExceptionCode;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.jwt.exception.TokenException;
import org.example.merong.jwt.exception.TokenExceptionCode;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenParser {

    private final SecretKey secretKey;
    private final ObjectMapper objectMapper;

    private Claims parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException expiredJwtException) {
            return expiredJwtException.getClaims();
        } catch (MalformedJwtException malformedJwtException) {
            throw new TokenException(FilterExceptionCode.MALFORMED_JWT_REQUEST);
        } catch (SignatureException signatureException) {
            throw new TokenException(TokenExceptionCode.NOT_VALID_SIGNATURE);
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new TokenException(TokenExceptionCode.NOT_VALID_CONTENT);
        }
    }

    public boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration().before(new Date());
    }

    public UserAuth getUserAuth(String token) {
        Claims claims = parseToken(token);

        return UserAuth.builder()
                .id(Long.valueOf(claims.getId()))
                .email(claims.getSubject())
                .build();
    }

    public Date getTokenExpiration(String token) {
        Claims claims = parseToken(token);

        return claims.getExpiration();
    }
}
