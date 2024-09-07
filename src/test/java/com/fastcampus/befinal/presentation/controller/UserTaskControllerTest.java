package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.TaskFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.TaskDto;
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

import java.time.LocalDateTime;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.MyTaskSuccessCode.CHECK_MY_TASK_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserTaskController 테스트")
@WebMvcTest(UserTaskController.class)
@Import(SecurityConfig.class)
class UserTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskFacade taskFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자 작업 조회 요청 성공시, 200 OK와 정상 응답을 반환")
    void checkMyTaskTest() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .userId("testID")
                .name("테스트유저")
                .password("password")
                .phoneNumber("01012345678")
                .empNumber("12345678")
                .email("test@test.com")
                .signUpDateTime(LocalDateTime.now().minusDays(10))
                .finalLoginDateTime(LocalDateTime.now().minusDays(5))
                .role(USER_AUTHORITY)
                .build();

        UserDetailsInfo userDetailsInfo = UserDetailsInfo.from(user);

        TaskDto.AdCountResponse adCountResponse = TaskDto.AdCountResponse.builder()
                .myTotalAd(1)
                .myDoneAd(1)
                .myNotDoneAd(0)
                .build();

        TaskDto.AdvertisementListResponse adResponse = TaskDto.AdvertisementListResponse.builder()
                .adId("A00001")
                .media("동아일보")
                .category("음식")
                .product("상품명")
                .advertiser("광고주")
                .state(true)
                .issue(true)
                .build();

        TaskDto.TaskListResponse taskListResponse = TaskDto.TaskListResponse.builder()
                .totalElements(1L)
                .cursorInfo(new TaskDto.CursorInfo(true, "A00001"))
                .advertisementList(List.of(adResponse))
                .build();

        TaskDto.TaskResponse response = TaskDto.TaskResponse.builder()
                .adCount(adCountResponse)
                .taskList(taskListResponse)
                .build();

        doReturn(response)
                .when(taskFacade)
                .loadFilterMyTask(anyString(), any(TaskDto.FilterConditionRequest.class));

        // when - media : 동아일보, 검수 상태 : 완료
        TaskDto.FilterConditionRequest firstRequest = TaskDto.FilterConditionRequest.builder()
                .media(List.of("동아일보"))
                .state(true)
                .build();

        ResultActions firstPerform = mockMvc.perform(post("/api/v1/user/my-task")
                .with(user(userDetailsInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstRequest))
                .accept(MediaType.APPLICATION_JSON));

        // then
        firstPerform.andExpect(status().is(CHECK_MY_TASK_SUCCESS.getHttpStatus().value()))
                .andExpect(jsonPath("code").value(CHECK_MY_TASK_SUCCESS.getCode()))
                .andExpect(jsonPath("message").value(CHECK_MY_TASK_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data.taskList.advertisementList[0].media").value("동아일보"))
                .andExpect(jsonPath("$.data.taskList.advertisementList[0].adId").value("A00001"))
                .andExpect(jsonPath("$.data.taskList.advertisementList[0].state").value(true));
    }
}