package com.fastcampus.befinal.domain.info;

import lombok.Builder;

public class AuthInfo {
    @Builder
    public record CheckIdTokenInfo(
        String token
    ) {
        public static CheckIdTokenInfo from(String token) {
            return CheckIdTokenInfo.builder()
                .token(token)
                .build();
        }
    }
}
