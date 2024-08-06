package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.domain.info.TokenInfo;
import lombok.Builder;

@Builder
public record ReissueTokenResponse(
    String accessToken,
    String refreshToken
) {
    public static ReissueTokenResponse from(TokenInfo tokenInfo) {
        return ReissueTokenResponse.builder()
            .accessToken(tokenInfo.getAccessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }
}
