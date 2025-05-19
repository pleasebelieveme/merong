package org.example.merong.common.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.merong.common.filter.constants.FilterConstants;
import org.example.merong.jwt.constants.JwtConstants;
import org.example.merong.jwt.service.JwtService;

public class AccessJwtFilter extends BaseJwtFilter {

    public AccessJwtFilter(JwtService jwtService) {
        super(jwtService);
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return (FilterConstants.WHITE_LIST.stream()
                .anyMatch(uri::contains))
                || (uri.equals(FilterConstants.USER_CRUD) && method.equals("POST"))
                || (uri.equals(FilterConstants.REISSUE));
    }

    @Override
    protected String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(JwtConstants.AUTH_HEADER);
    }

    @Override
    protected boolean shouldCheckBlackList() {
        return true;
    }
}
