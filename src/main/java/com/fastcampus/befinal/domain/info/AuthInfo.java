package com.fastcampus.befinal.domain.info;

import lombok.Builder;

public class AuthInfo {
    @Builder
    public record UserInfo(
        String id
    ) {
        public static UserInfo from(String userId) {
            return UserInfo.builder()
                .id(userId)
                .build();
        }
    }

    @Builder
    public record JwtInfo(
        String accessToken,
        String refreshToken
    ) {
        public static JwtInfo of(String accessToken, String refreshToken) {
            return JwtInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
    }
}
