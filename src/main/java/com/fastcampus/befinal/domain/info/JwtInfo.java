package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDateTime;

public class JwtInfo {
    @Builder
    public record UserInfo(
        String id
    ) {
        public static JwtInfo.UserInfo from(String userId) {
            return JwtInfo.UserInfo.builder()
                .id(userId)
                .build();
        }
    }

    @Builder
    public record TokenInfo(
        String accessToken,
        String refreshToken
    ) {
        public static JwtInfo.TokenInfo of(String accessToken, String refreshToken) {
            return JwtInfo.TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
    }

    @Builder
    public record RefreshTokenInfo(
        String userId,
        String token,
        LocalDateTime creationDateTime,
        LocalDateTime expirationDateTime
    ) {
        public static RefreshTokenInfo of(String userId, String token, LocalDateTime creationDateTime,
                                          LocalDateTime expirationDateTime) {
            return RefreshTokenInfo.builder()
                .userId(userId)
                .token(token)
                .creationDateTime(creationDateTime)
                .expirationDateTime(expirationDateTime)
                .build();
        }
    }
}
