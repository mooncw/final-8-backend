package com.fastcampus.befinal.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import static com.fastcampus.befinal.common.contant.AuthConstant.NOT_BLANK_ACCESSTOKEN;
import static com.fastcampus.befinal.common.contant.AuthConstant.NOT_BLANK_REFRESHTOKEN;

public class AuthDto {
    public record SignInRequest(
        String userId,
        String password
    ) {}

    public record ReissueJwtRequest(
        @NotBlank(message = NOT_BLANK_ACCESSTOKEN)
        String accessToken,

        @NotBlank(message = NOT_BLANK_REFRESHTOKEN)
        String refreshToken
    ) {}

    public record ReissueJwtResponse(
        String accessToken,
        String refreshToken
    ) {}
}
