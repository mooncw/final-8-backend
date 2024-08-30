package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.BoardFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
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

import java.util.ArrayList;

import static com.fastcampus.befinal.common.response.success.info.DashboardSuccessCode.CHECK_DASHBOARD_SUCCESS;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserBoardController 테스트")
@WebMvcTest(UserBoardController.class)
@Import(SecurityConfig.class)
class UserBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardFacade boardFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Test
    @WithMockUser(username = "testID", authorities = "USER_AUTHORITY")
    @DisplayName("사용자 대시보드 요청 성공시, 200 OK와 정상 응답을 반환")
    void getBoardDataTest() throws Exception {
        // given
        DashboardDto.DashboardDataResponse response = new DashboardDto.DashboardDataResponse(
                new DashboardDto.AdCount(1, 1, 1, 1, 0, 0),
                new ArrayList<>(), new ArrayList<>()
        );

        doReturn(response)
                .when(boardFacade)
                .loadUserDashboardData("testID");

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/dashboard")
                .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is(CHECK_DASHBOARD_SUCCESS.getHttpStatus().value()))
                .andExpect(jsonPath("code").value(CHECK_DASHBOARD_SUCCESS.getCode()))
                .andExpect(jsonPath("message").value(CHECK_DASHBOARD_SUCCESS.getMessage()));
    }
}
