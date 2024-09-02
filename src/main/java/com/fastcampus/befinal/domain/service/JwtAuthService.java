package com.fastcampus.befinal.domain.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtAuthService {
    String resolveAuthorizationHeader(HttpServletRequest request);

    void setAuthentication(String jwt);

    void setAuthenticationBearer(String jwt);
}
