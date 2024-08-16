package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class JwtCommand {
    @Builder
    public record CreateJwtRequest(
        String userId
    ) {}

    public record ReissueJwtRequest(
        String accessToken,
        String refreshToken
    ) {}
}
