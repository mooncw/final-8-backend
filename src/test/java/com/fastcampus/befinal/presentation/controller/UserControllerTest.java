package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.UPDATE_PASSWORD_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.UPDATE_USER_SUCCESS;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 테스트")
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("회원정보 변경 요청 성공시, 200 OK 및 정상 응답을 반환")
    void updateUserTest() throws Exception {
        //given
        UserDto.UserUpdateRequest request = UserDto.UserUpdateRequest.builder()
            .phoneNumber("01011112222")
            .email("hong@hong.com")
            .certNoCheckToken("aaaa-aaaa-aaaa")
            .build();

        UserDetailsInfo userInfo = UserDetailsInfo.builder().build();

        doNothing()
            .when(userFacade)
            .updateUser(userInfo, request);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v1/user/info")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then

        perform.andExpect(status().is(UPDATE_USER_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(UPDATE_USER_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(UPDATE_USER_SUCCESS.getMessage()));
    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("비밀번호 변경 요청 성공시, 200 OK 및 정상 응답을 반환")
    void updatePasswordTest() throws Exception {
        //given
        UserDto.PasswordUpdateRequest request = UserDto.PasswordUpdateRequest.builder()
            .currentPassword("0000")
            .newPassword("1111")
            .build();

        UserDetailsInfo userInfo = UserDetailsInfo.builder().build();

        doNothing()
            .when(userFacade)
            .updatePassword(userInfo, request);

        ResultActions perform = mockMvc.perform(put("/api/v1/user/password")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(UPDATE_PASSWORD_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(UPDATE_PASSWORD_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(UPDATE_PASSWORD_SUCCESS.getMessage()));
    }

}