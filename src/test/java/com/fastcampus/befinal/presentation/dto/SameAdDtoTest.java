package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.TaskConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 도메인 요청 Dto 테스트")
public class SameAdDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("동일광고 리스트 조건 검증 테스트 - Size")
    void whenSameAdFilterConditionRequestMismatchSize_thenValidationFails() {
        // given
        SameAdDto.SameAdFilterConditionRequest request = SameAdDto.SameAdFilterConditionRequest.builder()
            .keyword("a")
            .build();

        // when
        Set<ConstraintViolation<SameAdDto.SameAdFilterConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(messages).isEqualTo(Set.of(MIN_LENGTH_KEYWORD));
    }

    @Test
    @DisplayName("동일광고 리스트 조건 검증 테스트 - Pattern")
    void whenSameAdFilterConditionRequestMismatchPattern_thenValidationFails() {
        // given
        SameAdDto.SameAdFilterConditionRequest request = SameAdDto.SameAdFilterConditionRequest.builder()
            .cursorId("202413a0000")
            .period("2024-09-A")
            .build();

        // when
        Set<ConstraintViolation<SameAdDto.SameAdFilterConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(messages).isEqualTo(Set.of(PATTERN_MISMATCH_AD_ID, PATTERN_MISMATCH_PERIOD));
    }
}
