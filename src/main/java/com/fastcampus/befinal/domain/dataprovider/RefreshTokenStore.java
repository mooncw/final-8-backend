package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.JwtInfo;

public interface RefreshTokenStore {
    void store(JwtInfo.RefreshTokenInfo info);
}
