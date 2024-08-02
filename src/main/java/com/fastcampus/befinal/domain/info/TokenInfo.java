package com.fastcampus.befinal.domain.info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {
    private String accessToken;
    private String refreshToken;

    public static TokenInfo of(String accessToken, String refreshToken) {
        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
