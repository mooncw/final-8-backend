package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AdminConstant.*;
import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;

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
    public record RejectUser(
        @Schema(example = SWAGGER_USER_EMP_NUMBER)
        @NotBlank(message = NOT_BLANK_USER_EMP_NUMBER, groups = RequestValidationGroups.NotBlankGroup.class)
        @Size(min = 8, max = 8, message = SIZE_MISMATCH_USER_EMP_NUMBER, groups = RequestValidationGroups.SizeGroup.class)
        @Pattern(regexp = "^\\d+$", message = PATTERN_MISMATCH_USER_EMP_NUMBER, groups = RequestValidationGroups.PatternGroup.class)
        String empNo
    ) {}

    @Builder
    public record RejectUserRequest(
        @NotEmpty(message = NOT_EMPTY_USER_LIST, groups = RequestValidationGroups.NotEmptyGroup.class)
        List<@Valid RejectUser> userList
    ) {}

    @Builder
    public record FindUserTaskListRequest(
        @Schema(example = SWAGGER_INTEGER_CURSOR_ID)
        @NotNull(message = NOT_NULL_CURSOR_ID, groups = RequestValidationGroups.NotNullGroup.class)
        @Min(value = 1, message = SIZE_MISMATCH_INTEGER_CURSOR_ID, groups = RequestValidationGroups.SizeGroup.class)
        Integer cursorId,

        @Schema(example = SWAGGER_USER_TASK_SORTED)
        @NotBlank(message = NOT_BLANK_USER_TASK_SORTED, groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "EmpNo|DoneDesc|DoneAsc|DoneRatioDesc|DoneRatioAsc", groups = RequestValidationGroups.PatternGroup.class)
        String sorted,

        @Schema(example = SWAGGER_PERIOD)
        @NotBlank(message = NOT_BLANK_PERIOD, groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "^[1-9][0-9]{3}-(0?[1-9]|1[0-2])-[12]$", groups = RequestValidationGroups.PatternGroup.class)
        String period
    ) {}

    @Builder
    public record SignUpUserInfo(
        Long cursorId,
        String name,
        String empNo,
        String phoneNumber,
        String email,
        String signUpRequestDateTime
    ) {}

    @Builder
    public record FindSignUpUserListResponse(
        Long totalElements,
        Long currentCursorId,
        List<SignUpUserInfo> contents
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

    @Builder
    public record UserTaskInfo(
        Long id,
        String empNo,
        String name,
        Integer totalAd,
        Integer notDoneAd,
        Integer doneAd,
        Integer doneRatio
    ) {}

    @Builder
    public record FindUserTaskListResponse(
        Long totalElements,
        Long currentCursorId,
        List<UserTaskInfo> contents
    ) {}
}
