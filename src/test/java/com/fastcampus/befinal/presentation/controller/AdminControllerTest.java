package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AdminFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.AdminDto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.ADMIN_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.AdminSuccessCode.APPROVE_USER_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.AdminSuccessCode.FIND_SIGN_UP_USER_LIST_SUCCESS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AdminController 테스트")
@WebMvcTest(controllers = AdminController.class)
@Import(SecurityConfig.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminFacade adminFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY)
    @DisplayName("회원가입 승인 요청 성공시, 200 OK와 정상 응답을 반환")
    void approveUserTest() throws Exception {
        //given
        AdminDto.ApproveUser approveUser = AdminDto.ApproveUser.builder()
            .empNo("11111111")
            .build();

        AdminDto.ApproveUserRequest request =AdminDto.ApproveUserRequest.builder()
            .userList(List.of(approveUser))
            .build();

        doNothing()
            .when(adminFacade)
            .approveUser(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/admin/approve-user")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(APPROVE_USER_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(APPROVE_USER_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(APPROVE_USER_SUCCESS.getMessage()));
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY)
    @DisplayName("회원가입 신청 유저 목록 조회 성공시, 200 OK와 정상 응답을 반환")
    void findSignUpUserListTest() throws Exception {
        //given
        Long requestParam = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        AdminDto.SignUpUserInfo info = AdminDto.SignUpUserInfo.builder()
            .cursorId(2L)
            .name("홍길동")
            .empNo("11111111")
            .phoneNumber("01011112222")
            .email("hong@hong.com")
            .signUpRequestDateTime(LocalDateTime.now().minusDays(5).format(formatter))
            .build();

        AdminDto.FindSignUpUserListResponse response = AdminDto.FindSignUpUserListResponse.builder()
            .totalElements(1L)
            .currentCursorId(2L)
            .contents(List.of(info))
            .build();

        doReturn(response)
            .when(adminFacade)
            .findSignUpUserScroll(requestParam);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/admin/approve-user")
            .param("cursorId", "2")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8));

        //then
        perform.andExpect(status().is(FIND_SIGN_UP_USER_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FIND_SIGN_UP_USER_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(FIND_SIGN_UP_USER_LIST_SUCCESS.getMessage()));
    }
}
