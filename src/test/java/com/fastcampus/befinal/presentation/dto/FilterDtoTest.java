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

import static com.fastcampus.befinal.common.contant.TaskConstant.PATTERN_MISMATCH_PERIOD;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Filter 도메인 요청 Dto 테스트")
class FilterDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("Filter 매체/업종명 리스트 조회 검증 테스트 - Pattern")
    void whenFilterMismatchPattern_thenValidationFails() {
        FilterDto.ConditionRequest request = FilterDto.ConditionRequest.builder()
            .keyword("키워드")
            .period("2024-9-3")
            .build();

        //when
        Set<ConstraintViolation<FilterDto.ConditionRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_PERIOD));
    }

}