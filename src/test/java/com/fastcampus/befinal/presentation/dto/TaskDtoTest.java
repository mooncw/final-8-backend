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

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.AuthConstant.NOT_BLANK_CERTIFICATION_NUMBER_CHECK_TOKEN;
import static com.fastcampus.befinal.common.contant.TaskConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 도메인 요청 Dto 테스트")
public class TaskDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("나의 작업 리스트 조건 검증 테스트 - NotBlank")
    void whenSignUpRequestIsBlank_thenValidationFails() {
        //given
        TaskDto.FilterConditionRequest request = TaskDto.FilterConditionRequest.builder()
            .cursorInfo(TaskDto.CursorInfo.builder()
                .cursorState(true)
                .cursorId(" ")
                .build())
            .build();

        //when
        Set<ConstraintViolation<TaskDto.FilterConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_CURSOR_AD_ID));
    }

    @Test
    @DisplayName("나의 작업 리스트 조건 검증 테스트 - Size")
    void whenFilterConditionRequestMismatchSize_thenValidationFails() {
        // given
        TaskDto.FilterConditionRequest request = TaskDto.FilterConditionRequest.builder()
            .keyword("a")
            .build();

        // when
        Set<ConstraintViolation<TaskDto.FilterConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        assertThat(message).isEqualTo(Set.of(MIN_LENGTH_KEYWORD));
    }

    @Test
    @DisplayName("나의 작업 리스트 조건 검증 테스트 - Pattern")
    void whenFilterConditionRequestMismatchPattern_thenValidationFails() {
        // given
        TaskDto.FilterConditionRequest request = TaskDto.FilterConditionRequest.builder()
            .period("2024-13-3")
            .cursorInfo(TaskDto.CursorInfo.builder()
                .cursorId("Aaaaaa").build())
            .build();

        // when
        Set<ConstraintViolation<TaskDto.FilterConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(messages).isEqualTo(Set.of(PATTERN_MISMATCH_PERIOD, PATTERN_MISMATCH_AD_ID));

    }
}