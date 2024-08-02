package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;

public interface JwtTokenService {
    TokenInfo createToken(UserInfo user);
}
