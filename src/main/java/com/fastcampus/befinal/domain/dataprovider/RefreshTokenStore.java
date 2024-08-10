package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.RefreshToken;

public interface RefreshTokenStore {
    void store(RefreshToken refreshToken);
}
