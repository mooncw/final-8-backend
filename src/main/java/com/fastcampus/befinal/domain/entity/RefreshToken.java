package com.fastcampus.befinal.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class RefreshToken {
    private String userId;

    private String token;

    private ZonedDateTime creationTime;

    private ZonedDateTime expirationTime;
}
