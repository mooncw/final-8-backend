package com.fastcampus.befinal.application.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Auth 도메인 요청 Dto 테스트")
public class AuthDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("JWT 재발급 요청 검증 테스트 - NotBlank")
    void whenReissueJwtIsBlank_thenValidationFails() {
        //given
        AuthDto.ReissueJwtRequest request = AuthDto.ReissueJwtRequest.builder()
            .accessToken(" ")
            .refreshToken(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.ReissueJwtRequest>> violations = validator.validate(request, RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_ACCESSTOKEN, NOT_BLANK_REFRESHTOKEN));
    }

    @Test
    @DisplayName("회원가입 요청 검증 테스트 - NotBlank")
    void whenSignUpRequestIsBlank_thenValidationFails() {
        //given
        AuthDto.SignUpRequest request = AuthDto.SignUpRequest.builder()
            .name(" ")
            .phoneNumber(" ")
            .id(" ")
            .password(" ")
            .empNo(" ")
            .email(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request, RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_NAME, NOT_BLANK_PHONE_NUMBER, NOT_BLANK_USER_ID,
            NOT_BLANK_USER_PASSWORD, NOT_BLANK_USER_EMP_NOMBER, NOT_BLANK_USER_EMAIL));
    }

    @Test
    @DisplayName("회원가입 요청 검증 테스트 - Size")
    void whenSignUpRequestMismatchSize_thenValidationFails() {
        //given
        AuthDto.SignUpRequest request = AuthDto.SignUpRequest.builder()
            .name("가")
            .phoneNumber("1")
            .id("a")
            .password("a1")
            .empNo("1")
            .email("test@test.com")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request, RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_NAME, SIZE_MISMATCH_PHONE_NUMBER, SIZE_MISMATCH_USER_ID,
            SIZE_MISMATCH_USER_PASSWORD, SIZE_MISMATCH_USER_EMP_NOMBER));
    }

    @Test
    @DisplayName("회원가입 요청 검증 테스트 - Pattern, ComplexPattern, Email")
    void whenSignUpRequestMismatchPatternAndInvalidFormat_thenValidationFails() {
        //given
        AuthDto.SignUpRequest request = AuthDto.SignUpRequest.builder()
            .name("a")
            .phoneNumber("ㅁ")
            .id("가")
            .password("aa")
            .empNo("a")
            .email("a")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request, RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_NAME, PATTERN_MISMATCH_PHONE_NUMBER, PATTERN_MISMATCH_USER_ID,
            PATTERN_MISMATCH_USER_PASSWORD, PATTERN_MISMATCH_USER_EMP_NOMBER, INVALID_FORMAT_USER_EMAIL));
    }
}
