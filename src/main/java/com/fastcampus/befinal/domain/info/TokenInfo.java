package com.fastcampus.befinal.domain.info;

import lombok.Builder;

@Builder
public record TokenInfo(
    String accessToken,
    String refreshToken
) {
    public static TokenInfo of(String accessToken, String refreshToken) {
        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
