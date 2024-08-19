package com.fastcampus.befinal.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;

public class AuthDto {
    public record SignInRequest(
        String userId,
        String password
    ) {}

    @Builder
    @Schema(description = "JWT 재발급 request")
    public record ReissueJwtRequest(
        @NotBlank(message = NOT_BLANK_ACCESSTOKEN)
        @Schema(description = "예시 accessToken 형태", example = SWAGGER_REISSUE_REQUEST_ACCESSTOKEN)
        String accessToken,

        @NotBlank(message = NOT_BLANK_REFRESHTOKEN)
        @Schema(description = "예시 refreshToken 형태", example = SWAGGER_REISSUE_REQUEST_REFRESHTOKEN)
        String refreshToken
    ) {}

    @Builder
    public record ReissueJwtResponse(
        String accessToken,
        String refreshToken
    ) {}
}
