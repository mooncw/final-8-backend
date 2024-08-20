package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ComplexPattern;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;

public class AuthDto {
    @Builder
    public record SignUpRequest(
        @NotBlank(message = NOT_BLANK_USER_NAME, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 2, max = 4, message = SIZE_MISMATCH_USER_NAME, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^[가-힣]+$", message = PATTERN_MISMATCH_USER_NAME, groups = RequestValidationGroups.PatternGroup.class)
        String name,

        @NotBlank(message = NOT_BLANK_PHONE_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String phoneNumber,

        @NotBlank(message = NOT_BLANK_USER_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 4, max = 12, message = SIZE_MISMATCH_USER_ID, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^[a-zA-Z]+$", message = PATTERN_MISMATCH_USER_ID, groups = RequestValidationGroups.PatternGroup.class)
        String id,

        @NotBlank(message = NOT_BLANK_USER_PASSWORD, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 16, message = SIZE_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.SizeGroup.class)
        @ComplexPattern(patterns = { ".*[a-zA-Z].*", ".*\\d.*", ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*" },
            minMatches = 2, message = PATTERN_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.PatternGroup.class)
        String password,

        @NotBlank(message = NOT_BLANK_USER_EMP_NOMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 8, message = SIZE_MISMATCH_USER_EMP_NOMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_USER_EMP_NOMBER, groups = RequestValidationGroups.PatternGroup.class)
        String empNo,

        @NotBlank(message = NOT_BLANK_USER_EMAIL, groups = RequestValidationGroups.NotBlankGroup.class)
        @Email(message = INVALID_FORMAT_USER_EMAIL, groups = RequestValidationGroups.PatternGroup.class)
        String email
    ) {}

    public record SignInRequest(
        String userId,
        String password
    ) {}

    @Builder
    @Schema(description = "JWT 재발급 request")
    public record ReissueJwtRequest(
        @NotBlank(message = NOT_BLANK_ACCESSTOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        @Schema(description = "예시 accessToken 형태", example = SWAGGER_REISSUE_REQUEST_ACCESSTOKEN)
        String accessToken,

        @NotBlank(message = NOT_BLANK_REFRESHTOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        @Schema(description = "예시 refreshToken 형태", example = SWAGGER_REISSUE_REQUEST_REFRESHTOKEN)
        String refreshToken
    ) {}

    @Builder
    public record ReissueJwtResponse(
        String accessToken,
        String refreshToken
    ) {}
}
