package org.example.merong.common.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.merong.common.filter.constants.FilterConstants;
import org.example.merong.jwt.constants.JwtConstants;
import org.example.merong.jwt.service.JwtService;

public class RefreshJwtFilter extends BaseJwtFilter {

    public RefreshJwtFilter(JwtService jwtService) {
        super(jwtService);
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !uri.equals(FilterConstants.REISSUE);
    }

    @Override
    protected String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(JwtConstants.REFRESH_HEADER);
    }

    @Override
    protected boolean shouldCheckBlackList() {
        return false;
    }
}

