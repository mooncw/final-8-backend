package com.fastcampus.befinal.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class RefreshToken {
    private String userId;

    private String token;

    private ZonedDateTime creationTime;

    private ZonedDateTime expirationTime;

    @Builder
    public RefreshToken(String userId, String token, ZonedDateTime creationTime, ZonedDateTime expirationTime) {
        this.userId = userId;
        this.token = token;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
    }

    public static RefreshToken of(String userId, String token, ZonedDateTime creationTime, ZonedDateTime expirationTime) {
        return RefreshToken.builder()
            .userId(userId)
            .token(token)
            .creationTime(creationTime)
            .expirationTime(expirationTime)
            .build();
    }
}
