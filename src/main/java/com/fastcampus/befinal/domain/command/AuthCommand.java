package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class AuthCommand {
    public record SignInRequest(
        String userId,
        String password
    ) {}

    @Builder
    public record CreateJwtRequest(
        String userId
    ) {}

    public record ReissueJwtRequest(
        String accessToken,
        String refreshToken
    ) {}
}
