package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.AuthConstant.PATTERN_MISMATCH_USER_EMP_NUMBER;

public class AdminDto {
    @Builder
    public record ApproveUser(
        @Schema(example = SWAGGER_USER_EMP_NUMBER)
        @NotBlank(message = NOT_BLANK_USER_EMP_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 8, message = SIZE_MISMATCH_USER_EMP_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_USER_EMP_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String empNo
    ) {}

    @Builder
    public record ApproveUserRequest(
        @NotEmpty(message = NOT_EMPTY_USER_LIST, groups = RequestValidationGroups.NotEmptyGroup.class)
        List<@Valid ApproveUser> userList
    ) {}

    @Builder
    public record UserInfo(
        Long cursorId,
        String empNo,
        String name,
        String authority,
        String userId,
        String phoneNumber,
        String email,
        LocalDate signUpDate,
        String finalLoginDateTime
    ) {}

    @Builder
    public record FindUserListResponse(
        Long totalElements,
        Long currentCursorId,
        List<UserInfo> contents
    ) {}
}
