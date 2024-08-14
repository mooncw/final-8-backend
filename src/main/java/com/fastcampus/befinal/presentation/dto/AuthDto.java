package com.fastcampus.befinal.presentation.dto;

public class AuthDto {
    public record SignInRequest(
        String userId,
        String password
    ) {}

    public record ReissueJwtRequest(
        String accessToken,
        String refreshToken
    ) {}

    public record ReissueJwtResponse(
        String accessToken,
        String refreshToken
    ) {}
}
