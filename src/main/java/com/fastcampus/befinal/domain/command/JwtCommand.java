package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class JwtCommand {
    @Builder
    public record CreateJwtRequest(
        String userId
    ) {}

    @Builder
    public record ReissueJwtRequest(
        String accessToken,
        String refreshToken
    ) {}
}
