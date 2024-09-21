package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.SameAdFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.GET_SAME_ADVERTISEMENT_LIST_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("SameAdController 테스트")
@WebMvcTest(SameAdController.class)
@Import(SecurityConfig.class)
class SameAdControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SameAdFacade sameAdFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("동일광고 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void findSameAdListTest() throws Exception {
        // given
        TaskDto.SameAdvertisementListInfo adResponse = TaskDto.SameAdvertisementListInfo.builder()
            .adId("A00001")
            .media("동아일보")
            .category("음식")
            .product("상품명")
            .advertiser("광고주")
            .same(true)
            .build();

        TaskDto.SameTaskListInfo response = TaskDto.SameTaskListInfo.builder()
            .totalElements(1L)
            .cursorId("A00001")
            .sameAdvertisementList(List.of(adResponse))
            .build();

        doReturn(response)
            .when(sameAdFacade)
            .findSameAdList(any(TaskDto.SameAdFilterConditionRequest.class));

        // when
        TaskDto.SameAdFilterConditionRequest firstRequest = TaskDto.SameAdFilterConditionRequest.builder()
            .media(List.of("동아일보"))
            .same(true)
            .build();

        ResultActions firstPerform = mockMvc.perform(post("/api/v1/same-ad")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(firstRequest))
            .accept(MediaType.APPLICATION_JSON));

        // then
        firstPerform.andExpect(status().is(GET_SAME_ADVERTISEMENT_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(GET_SAME_ADVERTISEMENT_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(GET_SAME_ADVERTISEMENT_LIST_SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.sameAdvertisementList[0].media").value("동아일보"))
            .andExpect(jsonPath("$.data.sameAdvertisementList[0].adId").value("A00001"))
            .andExpect(jsonPath("$.data.sameAdvertisementList[0].same").value(true));
    }
}