package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.presentation.dto.ReissueTokenRequest;
import com.fastcampus.befinal.presentation.dto.ReissueTokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtAuthService {
    TokenInfo createTokenInfo(UserInfo user);

    String resolveAuthorizationHeader(HttpServletRequest request);

    void setAuthentication(String jwt);

    ReissueTokenResponse reissueTokenInfo(ReissueTokenRequest request);
}
