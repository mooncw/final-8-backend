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

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.UserConstant.INVALID_USER_UPDATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 도메인 요청 Dto 테스트")
public class UserDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("회원변경 요청 검증 테스트 - ValidUserUpdate")
    void whenUpdateUserRequestIsValid_thenValidationFails() {
        //given
        UserDto.UserUpdateRequest request = UserDto.UserUpdateRequest.builder()
            .phoneNumber(null)
            .email(null)
            .certNoCheckToken(null)
            .build();

        //when
        Set<ConstraintViolation<UserDto.UserUpdateRequest>> violations = validator.validate(request,
            RequestValidationGroups.CustomValidateGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(INVALID_USER_UPDATE_REQUEST));
    }

    @Test
    @DisplayName("회원변경 요청 검증 테스트 - Pattern, Email")
    void whenUpdateUserRequestMismatchPattern_thenValidationFails() {
        //given
        UserDto.UserUpdateRequest request = UserDto.UserUpdateRequest.builder()
            .phoneNumber("ㅁ")
            .email("g")
            .certNoCheckToken("1111")
            .build();

        //when
        Set<ConstraintViolation<UserDto.UserUpdateRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_PHONE_NUMBER,INVALID_FORMAT_USER_EMAIL));
    }

    @Test
    @DisplayName("회원변경 요청 검증 테스트 - Size")
    void whenUpdateUserRequestMismatchSize_thenValidationFails() {
        //given
        UserDto.UserUpdateRequest request = UserDto.UserUpdateRequest.builder()
            .phoneNumber("111")
            .email("g")
            .certNoCheckToken("1111")
            .build();

        //when
        Set<ConstraintViolation<UserDto.UserUpdateRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_PHONE_NUMBER));
    }
}
