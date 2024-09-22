package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.BoardFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.fastcampus.befinal.common.contant.AuthConstant.ADMIN_AUTHORITY;
import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.DashboardSuccessCode.CHECK_ADMIN_DASHBOARD_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.DashboardSuccessCode.CHECK_DASHBOARD_SUCCESS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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
    @DisplayName("사용자 대시보드 요청 성공시, 200 OK와 정상 응답을 반환")
    void getBoardDataTest() throws Exception {
        // given
        DashboardDto.DashboardDataResponse response = new DashboardDto.DashboardDataResponse(
                new DashboardDto.AdCount(1, 1, 1, 1, 0, 0),
                new ArrayList<>(), new ArrayList<>()
        );

        User user = User.builder()
            .id(1L)
            .userId("hong")
            .name("홍길동")
            .password("aaaa")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(USER_AUTHORITY)
            .build();

        UserDetailsInfo userDetailsInfo = UserDetailsInfo.from(user);

        doReturn(response)
                .when(boardFacade)
                .loadUserDashboardData(anyString());

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/dashboard")
                .with(user(userDetailsInfo))
                .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is(CHECK_DASHBOARD_SUCCESS.getHttpStatus().value()))
                .andExpect(jsonPath("code").value(CHECK_DASHBOARD_SUCCESS.getCode()))
                .andExpect(jsonPath("message").value(CHECK_DASHBOARD_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("관리자 대시보드 요청 성공시, 200 OK와 정상 응답을 반환")
    void getAdminBoardDataTest() throws Exception {
        // given
        DashboardDto.DashboardAdminDataResponse response = new DashboardDto.DashboardAdminDataResponse(
            new DashboardDto.AdminTimeline(1, 1),
            new DashboardDto.AdminAdCount(1,1,0),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        User user = User.builder()
            .id(1L)
            .userId("hong")
            .name("홍길동")
            .password("aaaa")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(ADMIN_AUTHORITY)
            .build();

        UserDetailsInfo userDetailsInfo = UserDetailsInfo.from(user);

        doReturn(response)
            .when(boardFacade)
            .loadAdminDashboardData();

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/dashboard/admin")
            .with(user(userDetailsInfo))
            .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is(CHECK_ADMIN_DASHBOARD_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(CHECK_ADMIN_DASHBOARD_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(CHECK_ADMIN_DASHBOARD_SUCCESS.getMessage()));
    }
}
