package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;

import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.GET_ADVERTISEMENT_DETAIL_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("IssueAdController 테스트")
@WebMvcTest(IssueAdController.class)
@Import(SecurityConfig.class)
class IssueAdControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueAdFacade issueAdFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("지적광고 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void findIssueAdListTest() throws Exception{
        //given
        TaskDto.AdvertisementListInfo adResponse = TaskDto.AdvertisementListInfo.builder()
            .adId("A00001")
            .media("동아일보")
            .category("음식")
            .product("상품명")
            .advertiser("광고주")
            .state(true)
            .issue(true)
            .build();

        TaskDto.TaskListInfo response = TaskDto.TaskListInfo.builder()
            .totalElements(1L)
            .cursorInfo(new TaskDto.CursorInfo(true, "A00001"))
            .advertisementList(List.of(adResponse))
            .build();

        doReturn(response)
            .when(issueAdFacade)
            .findIssueAdList(any(TaskDto.FilterConditionRequest.class));

        //when
        TaskDto.FilterConditionRequest firstRequest = TaskDto.FilterConditionRequest.builder()
            .media(List.of("동아일보"))
            .state(true)
            .build();

        ResultActions firstPerform = mockMvc.perform(post("/api/v1/issue-ad")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(firstRequest))
            .accept(MediaType.APPLICATION_JSON));

        // then
        firstPerform.andExpect(status().is(GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.advertisementList[0].media").value("동아일보"))
            .andExpect(jsonPath("$.data.advertisementList[0].adId").value("A00001"))
            .andExpect(jsonPath("$.data.advertisementList[0].state").value(true));

    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("지적광고 상세정보 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void getIssueAdDetailTest() throws Exception{
        //given
        String advertisementId = "A0001";
        IssueAdInfo.IssueAdReviewInfo review = IssueAdInfo.IssueAdReviewInfo.builder()
            .provisionArticle(1)
            .provisionContent("")
            .sentence("")
            .opinion("")
            .build();

        IssueAdDto.IssueAdDetailResponse response = IssueAdDto.IssueAdDetailResponse.builder()
            .id(advertisementId)
            .product("매일경제")
            .advertiser("아무개")
            .postDate(LocalDate.now())
            .assigneeName("홍길동")
            .modifierName("홍길동")
            .content("....")
            .reviewList(List.of(review))
            .build();

        doReturn(response)
            .when(issueAdFacade)
            .findIssueAdDetail(advertisementId);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/issue-ad/result/{advertisementId}", advertisementId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8));

        //then
        perform.andExpect(status().is(GET_ADVERTISEMENT_DETAIL_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(GET_ADVERTISEMENT_DETAIL_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(GET_ADVERTISEMENT_DETAIL_SUCCESS.getMessage()));
    }
}
