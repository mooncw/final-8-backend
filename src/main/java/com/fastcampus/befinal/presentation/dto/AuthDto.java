package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ComplexPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;

public class AuthDto {
    public record SignUpRequest(
        @NotBlank(message = NOT_BLANK_USER_NAME)
        @Size(min = 2, max = 4, message = SIZE_MISMATCH_USER_NAME)
        @Pattern(regexp = "^[가-힣]+$", message = PATTERN_MISMATCH_USER_NAME)
        String name,

        @NotBlank(message = NOT_BLANK_PHONE_NUMBER)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER)
        String phoneNumber,

        @NotBlank(message = NOT_BLANK_USER_ID)
        @Size(min = 4, max = 12, message = SIZE_MISMATCH_USER_ID)
        @Pattern(regexp = "^[a-zA-Z]+$", message = PATTERN_MISMATCH_USER_ID)
        String id,

        @NotBlank(message = NOT_BLANK_USER_PASSWORD)
        @Size(min = 8, max = 16, message = SIZE_MISMATCH_USER_PASSWORD)
        @ComplexPattern(patterns = { ".*[a-zA-Z].*", ".*\\d.*", ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*" },
            minMatches = 2, message = PATTERN_MISMATCH_USER_PASSWORD)
        String password,

        @NotBlank(message = NOT_BLANK_USER_EMP_NOMBER)
        @Size(min = 8, max = 8, message = SIZE_MISMATCH_USER_EMP_NOMBER)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_USER_EMP_NOMBER)
        String empNo,

        @NotBlank(message = NOT_BLANK_USER_EMAIL)
        @Email(message = INVALID_FORMAT_USER_EMAIL)
        String email
    ) {}

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
