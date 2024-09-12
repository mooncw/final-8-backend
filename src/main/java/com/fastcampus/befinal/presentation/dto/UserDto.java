package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ComplexPattern;
import com.fastcampus.befinal.common.annotation.ValidUserUpdate;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;
import static com.fastcampus.befinal.common.contant.UserConstant.*;

public class UserDto {
    @Builder
    @ValidUserUpdate(message = INVALID_USER_UPDATE_REQUEST, groups = RequestValidationGroups.CustomValidateGroup.class)
    public record UserUpdateRequest(
        @Schema(example = SWAGGER_UPDATE_PHONE_NUMBER)
        @Size(min = 11, max = 11, message = SIZE_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_PHONE_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String phoneNumber,

        @Schema(example = SWAGGER_UPDATE_EMAIL)
        @Email(message = INVALID_FORMAT_USER_EMAIL, groups = RequestValidationGroups.PatternGroup.class)
        String email,

        @Schema(example = SWAGGER_UPDATE_CERTIFICATION_NUMBER_CHECK_TOKEN)
        String certNoCheckToken
    ){}

    @Builder
    public record PasswordUpdateRequest(
        @Schema(example = SWAGGER_CURRENT_USER_PASSWORD)
        @NotBlank(message = NOT_BLANK_USER_PASSWORD, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 16, message = SIZE_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.SizeGroup.class)
        @ComplexPattern(patterns = { ".*[a-zA-Z].*", ".*\\d.*", ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*" },
            minMatches = 2, message = PATTERN_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.PatternGroup.class)
        String currentPassword,

        @Schema(example = SWAGGER_NEW_USER_PASSWORD)
        @NotBlank(message = NOT_BLANK_USER_PASSWORD, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 16, message = SIZE_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.SizeGroup.class)
        @ComplexPattern(patterns = { ".*[a-zA-Z].*", ".*\\d.*", ".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*" },
            minMatches = 2, message = PATTERN_MISMATCH_USER_PASSWORD, groups = RequestValidationGroups.PatternGroup.class)
        String newPassword
    ){}
}
