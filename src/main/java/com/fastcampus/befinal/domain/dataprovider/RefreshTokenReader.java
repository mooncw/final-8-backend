package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.RefreshToken;

public interface RefreshTokenReader {
    RefreshToken find(String userId);
}
