package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    TokenInfo createTokenInfo(UserInfo user);

    String resolveAuthorizationHeader(HttpServletRequest request);

    void setAuthentication(String jwt);
}
