package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AuthFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthController 테스트")
@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthFacade authFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 요청 성공시, 200 OK와 정상 응답을 반환")
    void signUpTest() throws Exception {
        //given
        AuthDto.SignUpRequest request = AuthDto.SignUpRequest.builder()
            .id("aaaa")
            .name("홍길동")
            .password("asdf1234")
            .phoneNumber("01011112222")
            .empNo("11111111")
            .email("hong@hong.com")
            .idCheckToken("aaaa-aaaa-aaaa")
            .certNoCheckToken("bbbb-bbbb-bbbb")
            .build();

        doNothing()
            .when(authFacade)
            .signUp(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(SIGN_UP_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(SIGN_UP_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(SIGN_UP_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("아이디 중복 확인 요청 성공시, 200 OK와 정상 응답을 반환")
    void checkIdDuplicationTest() throws Exception {
        //given
        AuthDto.CheckIdDuplicationRequest request = AuthDto.CheckIdDuplicationRequest.builder()
            .id("aaaa")
            .build();

        AuthDto.CheckIdDuplicationResponse response = AuthDto.CheckIdDuplicationResponse.builder()
            .idCheckToken("aaaa-aaaa-aaaa")
            .build();

        doReturn(response)
            .when(authFacade)
            .checkIdDuplication(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/check-id")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(CHECK_ID_DUPLICATION_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(CHECK_ID_DUPLICATION_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(CHECK_ID_DUPLICATION_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("인증번호 요청 성공시, 200 OK와 정상 응답을 반환")
    void sendCertificationNumberTest() throws Exception {
        //given
        AuthDto.SendCertificationNumberRequest request = AuthDto.SendCertificationNumberRequest.builder()
            .type("SignUp")
            .phoneNumber("01011112222")
            .build();

        doNothing()
            .when(authFacade)
            .sendCertificationNumber(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/cert-no")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(SEND_CERTIFICATION_NUMBER_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(SEND_CERTIFICATION_NUMBER_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(SEND_CERTIFICATION_NUMBER_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("인증번호 확인 성공시, 200 OK와 정상 응답을 반환")
    void checkCertificationNumberTest() throws Exception {
        //given
        AuthDto.CheckCertificationNumberRequest request = AuthDto.CheckCertificationNumberRequest.builder()
            .type("SignUp")
            .phoneNumber("01011112222")
            .certNo("111111")
            .build();

        AuthDto.CheckCertificationNumberResponse response = AuthDto.CheckCertificationNumberResponse.builder()
            .certNoCheckToken("bbbb-bbbb-bbbb")
            .build();

        doReturn(response)
            .when(authFacade)
            .checkCertificationNumber(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/check-cert-no")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(CHECK_CERTIFICATION_NUMBER_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(CHECK_CERTIFICATION_NUMBER_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(CHECK_CERTIFICATION_NUMBER_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("로그인 요청 성공시, 200 OK와 정상 응답을 반환")
    void signInTest() throws Exception {
        //given
        AuthDto.SignInRequest request = AuthDto.SignInRequest.builder()
            .id("aaaa")
            .password("asdf1234")
            .build();

        AuthDto.UserInfo userInfo = AuthDto.UserInfo.builder()
            .id(request.id())
            .name("홍길동")
            .phoneNumber("01011112222")
            .empNo("11111111")
            .email("hong@hong.com")
            .authority("작업자")
            .build();

        AuthDto.TokenInfo tokenInfo = AuthDto.TokenInfo.builder()
            .accessToken("aa1.ab1.ac1")
            .refreshToken("ra1.rb1.rc1")
            .build();

        AuthDto.SignInResponse response = AuthDto.SignInResponse.builder()
            .userInfo(userInfo)
            .tokenInfo(tokenInfo)
            .build();

        doReturn(response)
            .when(authFacade)
            .signIn(any(AuthDto.SignInRequest.class));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/signin")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(SIGN_IN_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(SIGN_IN_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(SIGN_IN_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("재발급 요청 성공시, 200 OK와 정상 응답을 반환")
    void reissueJwtTest() throws Exception {
        //given
        AuthDto.ReissueJwtRequest request = AuthDto.ReissueJwtRequest.builder()
            .accessToken("aa1.ab1.ac1")
            .refreshToken("ra1.rb1.rc1")
            .build();

        AuthDto.ReissueJwtResponse response = AuthDto.ReissueJwtResponse.builder()
            .accessToken("aa2.ab2.ac2")
            .refreshToken("ra2.rb2.rc2")
            .build();

        doReturn(response)
            .when(authFacade)
            .reissueJwt(any(AuthDto.ReissueJwtRequest.class));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/reissue")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(REISSUE_JWT_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(REISSUE_JWT_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(REISSUE_JWT_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("아이디 찾기 성공시, 200 OK와 정상 응답을 반환")
    void findUserId() throws Exception {
        // given
        AuthDto.FindIdRequest request = AuthDto.FindIdRequest.builder()
            .name("홍길동")
            .phoneNumber("01011112222")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        AuthDto.FindIdResponse response = AuthDto.FindIdResponse.builder()
            .userId("hong")
            .build();

        doReturn(response)
            .when(authFacade)
            .findId(any(AuthDto.FindIdRequest.class));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/find-id")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        // then
        perform.andExpect(status().is(FIND_ID_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FIND_ID_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(FIND_ID_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("비밀번호 찾기 성공시, 200 OK와 정상 응답을 반환")
    void findUserPassword() throws Exception {
        // given
        AuthDto.FindPasswordRequest request = AuthDto.FindPasswordRequest.builder()
            .userId("hong")
            .name("홍길동")
            .phoneNumber("01011112222")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        AuthDto.PasswordResetTokenResponse response = AuthDto.PasswordResetTokenResponse.builder()
            .passwordResetToken("pa1.pb1.pc1")
            .build();

        doReturn(response)
            .when(authFacade)
            .findPassword(any(AuthDto.FindPasswordRequest.class));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/auth/find-password")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        // then
        perform.andExpect(status().is(FIND_PASSWORD_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FIND_PASSWORD_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(FIND_PASSWORD_SUCCESS.getMessage()));
    }
}