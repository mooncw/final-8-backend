package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AuthFacade;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.REISSUE_JWT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("AuthController 테스트")
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthFacade authFacade;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    @DisplayName("재발급 요청 성공시, 200 OK와 정상 응답을 반환")
    void reissueAccessTokenTest() throws Exception {
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
        perform.andExpect(status().is(REISSUE_JWT.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(REISSUE_JWT.getCode()))
            .andExpect(jsonPath("message").value(REISSUE_JWT.getMessage()));
    }
}