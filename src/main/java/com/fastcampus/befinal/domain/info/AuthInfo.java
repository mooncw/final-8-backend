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

    @Builder
    public record CheckCertificationNumberTokenInfo(
        String token
    ) {
        public static CheckCertificationNumberTokenInfo from(String token) {
            return CheckCertificationNumberTokenInfo.builder()
                .token(token)
                .build();
        }
    }

    @Builder
    public record CheckTokenInfo(
        String token
    ) {
        public static CheckTokenInfo from(String token) {
            return CheckTokenInfo.builder()
                .token(token)
                .build();
        }
    }

    @Builder
    public record UserInfo(
        String id,
        String name,
        String phoneNumber,
        String empNo,
        String email,
        String authority
    ) {}
}
