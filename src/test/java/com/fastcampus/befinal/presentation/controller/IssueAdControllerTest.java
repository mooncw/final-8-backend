package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
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

import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
            .andExpect(jsonPath("message").value(GET_ADVERTISEMENT_DETAIL_SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.id").value(advertisementId));
    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("지적광고 검수 리뷰 저장 요청 시, 200 OK 및 정상 응답을 반환")
    void saveIssueAdReviewsTest() throws Exception {
        //given
        IssueAdDto.IssueAdReviewRequest request = IssueAdDto.IssueAdReviewRequest.builder().build();
        List<IssueAdDto.IssueAdReviewRequest> requests = List.of(request);

        doNothing()
            .when(issueAdFacade)
            .saveIssueAdReviews(requests);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/issue-ad/save-task")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(requests)));

        //then
        perform.andExpect(status().is(SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS.getMessage()));
    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("조항 리스트 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void getProvisionListTest() throws Exception {
        //given
        IssueAdDto.IssueAdProvisionResponse response = IssueAdDto.IssueAdProvisionResponse.builder()
            .id(1)
            .article(1)
            .content("1항 내용")
            .build();
        List<IssueAdDto.IssueAdProvisionResponse> responses = List.of(response);

        doReturn(responses)
            .when(issueAdFacade)
            .findProvisionList();

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/issue-ad/options/provision")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8));

        //then
        perform.andExpect(status().is(GET_PROVISION_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(GET_PROVISION_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(GET_PROVISION_LIST_SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data[0].id").value(response.id()))
            .andExpect(jsonPath("$.data[0].article").value(response.article()))
            .andExpect(jsonPath("$.data[0].content").value(response.content()));
    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("심의결정 리스트 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void getDecisionListTest() throws Exception {
        //given
        IssueAdDto.IssueAdDecisionResponse response = IssueAdDto.IssueAdDecisionResponse.builder()
            .id((long)1)
            .decision("수정 필요")
            .build();
        List<IssueAdDto.IssueAdDecisionResponse> responses = List.of(response);

        doReturn(responses)
            .when(issueAdFacade)
            .findDecisionList();

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/issue-ad/options/decision")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8));

        //then
        perform.andExpect(status().is(GET_DECISION_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(GET_DECISION_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(GET_DECISION_LIST_SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data[0].id").value(response.id()))
            .andExpect(jsonPath("$.data[0].decision").value(response.decision()));
    }

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("지적광고 심의결정 완료 요청 시, 200 OK 및 정상 응답을 반환")
    void saveIssueAdDecisionTest() throws Exception{
        //given
        IssueAdDto.IssueAdResultDecisionRequest request = IssueAdDto.IssueAdResultDecisionRequest.builder()
            .advertisementId("202409A0001")
            .decisionId((long) 1)
            .build();

        doNothing()
            .when(issueAdFacade)
            .saveIssueAdResultDecision(request);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/issue-ad/result/decision")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andExpect(status().is(SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS.getMessage()));
    }
}
