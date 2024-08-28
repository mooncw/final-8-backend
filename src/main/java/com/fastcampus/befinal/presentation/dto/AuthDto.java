package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ComplexPattern;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;

public class AuthDto {
    @Builder
    @Schema(description = "회원가입 request")
    public record SignUpRequest(
        @Schema(example = SWAGGER_SIGN_UP_USER_NAME)
        @NotBlank(message = NOT_BLANK_USER_NAME, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 2, max = 4, message = SIZE_MISMATCH_USER_NAME, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^[가-힣]+$", message = PATTERN_MISMATCH_USER_NAME, groups = RequestValidationGroups.PatternGroup.class)
        String name,

        @Schema(example = SWAGGER_SIGN_UP_PHONE_NUMBER)
        @NotBlank(message = NOT_BLANK_PHONE_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String phoneNumber,

        @Schema(example = SWAGGER_SIGN_UP_USER_ID)
        @NotBlank(message = NOT_BLANK_USER_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 4, max = 12, message = SIZE_MISMATCH_USER_ID, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^[a-zA-Z]+$", message = PATTERN_MISMATCH_USER_ID, groups = RequestValidationGroups.PatternGroup.class)
        String id,

        @Schema(example = SWAGGER_SIGN_UP_USER_PASSWORD)
        @NotBlank(message = NOT_BLANK_USER_PASSWORD, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 16, message = SIZE_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.SizeGroup.class)
        @ComplexPattern(patterns = { ".*[a-zA-Z].*", ".*\\d.*", ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*" },
            minMatches = 2, message = PATTERN_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.PatternGroup.class)
        String password,

        @Schema(example = SWAGGER_SIGN_UP_USER_EMP_NOMBER)
        @NotBlank(message = NOT_BLANK_USER_EMP_NOMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 8, message = SIZE_MISMATCH_USER_EMP_NOMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_USER_EMP_NOMBER, groups = RequestValidationGroups.PatternGroup.class)
        String empNo,

        @Schema(example = SWAGGER_SIGN_UP_USER_EMAIL)
        @NotBlank(message = NOT_BLANK_USER_EMAIL, groups = RequestValidationGroups.NotBlankGroup.class)
        @Email(message = INVALID_FORMAT_USER_EMAIL, groups = RequestValidationGroups.PatternGroup.class)
        String email,

        @Schema(example = SWAGGER_SIGN_UP_USER_ID_CHECK_TOKEN)
        @NotBlank(message = NOT_BLANK_USER_ID_CHECK_TOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        String idCheckToken,

        @Schema(example = SWAGGER_SIGN_UP_CERTIFICATION_NUMBER_CHECK_TOKEN)
        @NotBlank(message = NOT_BLANK_CERTIFICATION_NUMBER_CHECK_TOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        String certNoCheckToken
    ) {}

    @Builder
    @Schema(description = "ID 중복 확인 request")
    public record CheckIdDuplicationRequest(
        @Schema(example = SWAGGER_SIGN_UP_USER_ID)
        @NotBlank(message = NOT_BLANK_USER_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 4, max = 12, message = SIZE_MISMATCH_USER_ID, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^[a-zA-Z]+$", message = PATTERN_MISMATCH_USER_ID, groups = RequestValidationGroups.PatternGroup.class)
        String id
    ) {}

    @Builder
    @Schema(description = "인증번호 요청 request")
    public record SendCertificationNumberRequest(
        @Schema(example = SWAGGER_CERTIFICATION_NUMBER_TYPE)
        @NotBlank(message = NOT_BLANK_CERTIFICATION_TYPE, groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "SignUp", message = PATTERN_MISMATCH_CERTIFICATION_TYPE, groups = RequestValidationGroups.PatternGroup.class)
        String type,

        @Schema(example = SWAGGER_SIGN_UP_PHONE_NUMBER)
        @NotBlank(message = NOT_BLANK_PHONE_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String phoneNumber
    ) {}

    @Builder
    @Schema(description = "인증번호 확인 request")
    public record CheckCertificationNumberRequest(
        @Schema(example = SWAGGER_CERTIFICATION_NUMBER_TYPE)
        @NotBlank(message = NOT_BLANK_CERTIFICATION_TYPE, groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "SignUp", message = PATTERN_MISMATCH_CERTIFICATION_TYPE, groups = RequestValidationGroups.PatternGroup.class)
        String type,

        @NotBlank(message = NOT_BLANK_PHONE_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String phoneNumber,

        @NotBlank(message = NOT_BLANK_CERTIFICATION_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 6, max = 6, message = SIZE_MISMATCH_CERTIFICATION_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_CERTIFICATION_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String certNo
    ) {}

    public record SignInRequest(
        String userId,
        String password
    ) {}

    @Builder
    @Schema(description = "JWT 재발급 request")
    public record ReissueJwtRequest(
        @Schema(example = SWAGGER_REISSUE_REQUEST_ACCESSTOKEN)
        @NotBlank(message = NOT_BLANK_ACCESSTOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        String accessToken,

        @Schema(example = SWAGGER_REISSUE_REQUEST_REFRESHTOKEN)
        @NotBlank(message = NOT_BLANK_REFRESHTOKEN, groups = RequestValidationGroups.NotBlankGroup.class)
        String refreshToken
    ) {}

    @Builder
    public record ReissueJwtResponse(
        String accessToken,
        String refreshToken
    ) {}

    @Builder
    public record CheckIdDuplicationResponse(
        String idCheckToken
    ) {}

    @Builder
    public record CheckCertificationNumberResponse(
        String certNoCheckToken
    ) {}
}
