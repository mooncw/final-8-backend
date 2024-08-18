package com.fastcampus.befinal.application.dto;

import com.fastcampus.befinal.presentation.dto.AuthDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.AuthConstant.NOT_BLANK_ACCESSTOKEN;
import static com.fastcampus.befinal.common.contant.AuthConstant.NOT_BLANK_REFRESHTOKEN;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Auth 도메인 요청 Dto 테스트")
public class AuthDtoTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("JWT 재발급 요청 검증 테스트 - NotBlank")
    void whenReissueJwtIsBlank_thenValidationFails() {
        AuthDto.ReissueJwtRequest request = AuthDto.ReissueJwtRequest.builder()
            .accessToken(" ")
            .refreshToken(" ")
            .build();

        Set<ConstraintViolation<AuthDto.ReissueJwtRequest>> violations = validator.validate(request);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_ACCESSTOKEN, NOT_BLANK_REFRESHTOKEN));
    }
}
