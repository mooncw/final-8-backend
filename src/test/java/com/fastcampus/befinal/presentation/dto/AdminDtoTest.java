package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.AdminConstant.*;
import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Admin 도메인 요청 Dto 테스트")
public class AdminDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("회원가입 승인 요청 검증 테스트 - NotEmpty")
    void whenApproveUserRequestIsEmpty_thenValidationFails() {
        //given
        AdminDto.ApproveUserRequest request = AdminDto.ApproveUserRequest.builder()
            .userList(List.of())
            .build();

        //when
        Set<ConstraintViolation<AdminDto.ApproveUserRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotEmptyGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_EMPTY_USER_LIST));
    }

    @Test
    @DisplayName("회원가입 승인 요청 검증 테스트 - NotBlank")
    void whenApproveUserIsBlank_thenValidationFails() {
        //given
        AdminDto.ApproveUser request = AdminDto.ApproveUser.builder()
            .empNo(" ")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.ApproveUser>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("회원가입 승인 요청 검증 테스트 - Size")
    void whenApproveUserMismatchSize_thenValidationFails() {
        //given
        AdminDto.ApproveUser request = AdminDto.ApproveUser.builder()
            .empNo("1")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.ApproveUser>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("회원가입 승인 요청 검증 테스트 - Pattern")
    void whenApproveUserMismatchPattern_thenValidationFails() {
        //given
        AdminDto.ApproveUser request = AdminDto.ApproveUser.builder()
            .empNo("a")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.ApproveUser>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("회원가입 반려 요청 검증 테스트 - NotEmpty")
    void whenRejectUserRequestIsEmpty_thenValidationFails() {
        //given
        AdminDto.RejectUserRequest request = AdminDto.RejectUserRequest.builder()
            .userList(List.of())
            .build();

        //when
        Set<ConstraintViolation<AdminDto.RejectUserRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotEmptyGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_EMPTY_USER_LIST));
    }

    @Test
    @DisplayName("회원가입 반려 요청 검증 테스트 - NotBlank")
    void whenRejectUserIsBlank_thenValidationFails() {
        //given
        AdminDto.RejectUser request = AdminDto.RejectUser.builder()
            .empNo(" ")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.RejectUser>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("회원가입 반려 요청 검증 테스트 - Size")
    void whenRejectUserMismatchSize_thenValidationFails() {
        //given
        AdminDto.RejectUser request = AdminDto.RejectUser.builder()
            .empNo("1")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.RejectUser>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("회원가입 반려 요청 검증 테스트 - Pattern")
    void whenRejectUserMismatchPattern_thenValidationFails() {
        //given
        AdminDto.RejectUser request = AdminDto.RejectUser.builder()
            .empNo("a")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.RejectUser>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_EMP_NUMBER));
    }

    @Test
    @DisplayName("작업자 관리 정보 조회 요청 검증 테스트 - NotNull")
    void whenFindUserTaskListRequestIsNull_thenValidationFails() {
        //given
        AdminDto.FindUserTaskListRequest request = AdminDto.FindUserTaskListRequest.builder()
            .cursorId(null)
            .build();

        //when
        Set<ConstraintViolation<AdminDto.FindUserTaskListRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotNullGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_NULL_CURSOR_ID));
    }

    @Test
    @DisplayName("작업자 관리 정보 조회 요청 검증 테스트 - NotBlank")
    void whenFindUserTaskListRequestIsBlank_thenValidationFails() {
        //given
        AdminDto.FindUserTaskListRequest request = AdminDto.FindUserTaskListRequest.builder()
            .sorted(" ")
            .period(" ")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.FindUserTaskListRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_TASK_SORTED, NOT_BLANK_PERIOD));
    }

    @Test
    @DisplayName("작업자 관리 정보 조회 요청 검증 테스트 - Min")
    void whenFindUserTaskListRequestMismatchSize_thenValidationFails() {
        //given
        AdminDto.FindUserTaskListRequest request = AdminDto.FindUserTaskListRequest.builder()
            .cursorId(0)
            .build();

        //when
        Set<ConstraintViolation<AdminDto.FindUserTaskListRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_INTEGER_CURSOR_ID));
    }

    @Test
    @DisplayName("작업자 관리 정보 조회 요청 검증 테스트 - Pattern")
    void whenFindUserTaskListRequestMismatchPattern_thenValidationFails() {
        //given
        AdminDto.FindUserTaskListRequest request = AdminDto.FindUserTaskListRequest.builder()
            .sorted("EEEE")
            .period("0999-01-1")
            .build();

        //when
        Set<ConstraintViolation<AdminDto.FindUserTaskListRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_SORTED, PATTERN_MISMATCH_PERIOD));
    }
}
