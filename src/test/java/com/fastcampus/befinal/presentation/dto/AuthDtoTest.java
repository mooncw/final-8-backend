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
        Set<ConstraintViolation<AuthDto.ReissueJwtRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
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
            .idCheckToken(" ")
            .certNoCheckToken(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_NAME, NOT_BLANK_PHONE_NUMBER, NOT_BLANK_USER_ID,
            NOT_BLANK_USER_PASSWORD, NOT_BLANK_USER_EMP_NUMBER, NOT_BLANK_USER_EMAIL, NOT_BLANK_USER_ID_CHECK_TOKEN,
            NOT_BLANK_CERTIFICATION_NUMBER_CHECK_TOKEN));
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
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_NAME, SIZE_MISMATCH_PHONE_NUMBER, SIZE_MISMATCH_USER_ID,
            SIZE_MISMATCH_USER_PASSWORD, SIZE_MISMATCH_USER_EMP_NUMBER));
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
        Set<ConstraintViolation<AuthDto.SignUpRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_NAME, PATTERN_MISMATCH_PHONE_NUMBER, PATTERN_MISMATCH_USER_ID,
            PATTERN_MISMATCH_USER_PASSWORD, PATTERN_MISMATCH_USER_EMP_NUMBER, INVALID_FORMAT_USER_EMAIL));
    }

    @Test
    @DisplayName("ID 중복 확인 요청 검증 테스트 - NotBlank")
    void whenCheckIdDuplicationRequestIsBlank_thenValidationFails() {
        //given
        AuthDto.CheckIdDuplicationRequest request = AuthDto.CheckIdDuplicationRequest.builder()
            .id(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckIdDuplicationRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_ID));
    }

    @Test
    @DisplayName("ID 중복 확인 요청 검증 테스트 - Size")
    void whenCheckIdDuplicationRequestMismatchSize_thenValidationFails() {
        //given
        AuthDto.CheckIdDuplicationRequest request = AuthDto.CheckIdDuplicationRequest.builder()
            .id("a")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckIdDuplicationRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_ID));
    }

    @Test
    @DisplayName("ID 중복 확인 요청 검증 테스트 - Pattern")
    void whenCheckIdDuplicationRequestMismatchPatternAndInvalidFormat_thenValidationFails() {
        //given
        AuthDto.CheckIdDuplicationRequest request = AuthDto.CheckIdDuplicationRequest.builder()
            .id("가")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckIdDuplicationRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_ID));
    }

    @Test
    @DisplayName("인증 번호 전송 요청 검증 테스트 - NotBlank")
    void whenSendCertificationNumberRequestIsBlank_thenValidationFails() {
        //given
        AuthDto.SendCertificationNumberRequest request = AuthDto.SendCertificationNumberRequest.builder()
            .type(" ")
            .phoneNumber(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SendCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_CERTIFICATION_TYPE, NOT_BLANK_PHONE_NUMBER));
    }

    @Test
    @DisplayName("인증 번호 전송 요청 검증 테스트 - Size")
    void whenSendCertificationNumberRequestMismatchSize_thenValidationFails() {
        //given
        AuthDto.SendCertificationNumberRequest request = AuthDto.SendCertificationNumberRequest.builder()
            .phoneNumber("1")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SendCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_PHONE_NUMBER));
    }

    @Test
    @DisplayName("인증 번호 전송 요청 검증 테스트 - Pattern")
    void whenSendCertificationNumberRequestMismatchPatternAndInvalidFormat_thenValidationFails() {
        //given
        AuthDto.SendCertificationNumberRequest request = AuthDto.SendCertificationNumberRequest.builder()
            .type("A")
            .phoneNumber("ㅁ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SendCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_CERTIFICATION_TYPE, PATTERN_MISMATCH_PHONE_NUMBER));
    }

    @Test
    @DisplayName("인증 번호 확인 요청 검증 테스트 - NotBlank")
    void whenCheckCertificationNumberRequestIsBlank_thenValidationFails() {
        //given
        AuthDto.CheckCertificationNumberRequest request = AuthDto.CheckCertificationNumberRequest.builder()
            .type(" ")
            .phoneNumber(" ")
            .certNo(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_PHONE_NUMBER, NOT_BLANK_CERTIFICATION_NUMBER,
            NOT_BLANK_CERTIFICATION_TYPE));
    }

    @Test
    @DisplayName("인증 번호 확인 요청 검증 테스트 - Size")
    void whenCheckCertificationNumberRequestMismatchSize_thenValidationFails() {
        //given
        AuthDto.CheckCertificationNumberRequest request = AuthDto.CheckCertificationNumberRequest.builder()
            .phoneNumber("1")
            .certNo("1")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_PHONE_NUMBER, SIZE_MISMATCH_CERTIFICATION_NUMBER));
    }

    @Test
    @DisplayName("인증 번호 확인 요청 검증 테스트 - Pattern")
    void whenCheckCertificationNumberRequestMismatchPatternAndInvalidFormat_thenValidationFails() {
        //given
        AuthDto.CheckCertificationNumberRequest request = AuthDto.CheckCertificationNumberRequest.builder()
            .phoneNumber("ㅁ")
            .certNo("ㅁ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.CheckCertificationNumberRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_PHONE_NUMBER, PATTERN_MISMATCH_CERTIFICATION_NUMBER));
    }

    @Test
    @DisplayName("로그인 요청 검증 테스트 - NotBlank")
    void whenSignInRequestIsBlank_thenValidationFails() {
        //given
        AuthDto.SignInRequest request = AuthDto.SignInRequest.builder()
            .id(" ")
            .password(" ")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignInRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_ID, NOT_BLANK_USER_PASSWORD));
    }

    @Test
    @DisplayName("로그인 요청 검증 테스트 - Size")
    void whenSignInRequestMismatchSize_thenValidationFails() {
        //given
        AuthDto.SignInRequest request = AuthDto.SignInRequest.builder()
            .id("a")
            .password("a1")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignInRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_ID, SIZE_MISMATCH_USER_PASSWORD));
    }

    @Test
    @DisplayName("로그인 요청 검증 테스트 - Pattern, ComplexPattern")
    void whenSignInRequestMismatchPattern_thenValidationFails() {
        //given
        AuthDto.SignInRequest request = AuthDto.SignInRequest.builder()
            .id("가")
            .password("aa")
            .build();

        //when
        Set<ConstraintViolation<AuthDto.SignInRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        //then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_ID, PATTERN_MISMATCH_USER_PASSWORD));
    }

    @Test
    @DisplayName("아이디 찾기 요청 검증 테스트 - NotBlank")
    void whenFindIdRequestIsBlank_thenValidationFails() {
        // given
        AuthDto.FindIdRequest request = AuthDto.FindIdRequest.builder()
            .name(" ")
            .phoneNumber(" ")
            .certNoCheckToken(" ")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindIdRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_NAME, NOT_BLANK_PHONE_NUMBER, NOT_BLANK_CERTIFICATION_NUMBER_CHECK_TOKEN));
    }

    @Test
    @DisplayName("아이디 찾기 요청 검증 테스트 - Size")
    void whenFindIdRequestMismatchSize_thenValidationFails() {
        // given
        AuthDto.FindIdRequest request = AuthDto.FindIdRequest.builder()
            .name("박")
            .phoneNumber("0101234567")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindIdRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_NAME, SIZE_MISMATCH_PHONE_NUMBER));
    }

    @Test
    @DisplayName("아이디 찾기 요청 검증 테스트 - Pattern")
    void whenFindIdRequestMismatchPattern_thenValidationFails() {
        // given
        AuthDto.FindIdRequest request = AuthDto.FindIdRequest.builder()
            .name("박t2")
            .phoneNumber("a1234567890")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindIdRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_NAME, PATTERN_MISMATCH_PHONE_NUMBER));
    }

    @Test
    @DisplayName("비밀번호 찾기 요청 검증 테스트 - NotBlank")
    void whenFindPasswordRequestIsBlank_thenValidationFails() {
        // given
        AuthDto.FindPasswordRequest request = AuthDto.FindPasswordRequest.builder()
            .userId(" ")
            .name(" ")
            .phoneNumber(" ")
            .certNoCheckToken(" ")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindPasswordRequest>> violations = validator.validate(request,
            RequestValidationGroups.NotBlankGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(NOT_BLANK_USER_ID, NOT_BLANK_USER_NAME, NOT_BLANK_PHONE_NUMBER, NOT_BLANK_CERTIFICATION_NUMBER_CHECK_TOKEN));
    }

    @Test
    @DisplayName("비밀번호 찾기 요청 검증 테스트 - Size")
    void whenFindPasswordRequestMismatchSize_thenValidationFails() {
        // given
        AuthDto.FindPasswordRequest request = AuthDto.FindPasswordRequest.builder()
            .userId("par")
            .name("박")
            .phoneNumber("0101234567")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindPasswordRequest>> violations = validator.validate(request,
            RequestValidationGroups.SizeGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(SIZE_MISMATCH_USER_ID, SIZE_MISMATCH_USER_NAME, SIZE_MISMATCH_PHONE_NUMBER));
    }

    @Test
    @DisplayName("비밀번호 찾기 요청 검증 테스트 - Pattern")
    void whenFindPasswordRequestMismatchPattern_thenValidationFails() {
        // given
        AuthDto.FindPasswordRequest request = AuthDto.FindPasswordRequest.builder()
            .userId("박현준")
            .name("박t2")
            .phoneNumber("a1234567890")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        // when
        Set<ConstraintViolation<AuthDto.FindPasswordRequest>> violations = validator.validate(request,
            RequestValidationGroups.PatternGroup.class);
        Set<String> message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());

        // then
        assertThat(message).isEqualTo(Set.of(PATTERN_MISMATCH_USER_ID, PATTERN_MISMATCH_USER_NAME, PATTERN_MISMATCH_PHONE_NUMBER));
    }
}
