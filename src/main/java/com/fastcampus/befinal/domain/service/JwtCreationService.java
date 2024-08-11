package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.presentation.dto.ReissueTokenRequest;
import com.fastcampus.befinal.presentation.dto.ReissueTokenResponse;

public interface JwtCreationService {
    TokenInfo createTokenInfo(UserInfo user);

    ReissueTokenResponse reissueTokenInfo(ReissueTokenRequest request);
}
