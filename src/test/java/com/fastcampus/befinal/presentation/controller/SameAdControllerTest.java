package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.SameAdFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
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
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.FIND_SIMILARITY_LIST_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.GET_SAME_ADVERTISEMENT_LIST_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        SameAdDto.SameAdvertisementListInfo adResponse = SameAdDto.SameAdvertisementListInfo.builder()
            .adId("A00001")
            .media("동아일보")
            .category("음식")
            .product("상품명")
            .advertiser("광고주")
            .same(true)
            .build();

        SameAdDto.SameTaskListInfo response = SameAdDto.SameTaskListInfo.builder()
            .totalElements(1L)
            .cursorId("A00001")
            .sameAdvertisementList(List.of(adResponse))
            .build();

        doReturn(response)
            .when(sameAdFacade)
            .findSameAdList(any(SameAdDto.SameAdFilterConditionRequest.class));

        // when
        SameAdDto.SameAdFilterConditionRequest firstRequest = SameAdDto.SameAdFilterConditionRequest.builder()
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

    @Test
    @WithMockUser(authorities = USER_AUTHORITY)
    @DisplayName("동일 광고 유사율 리스트 조회 요청 시, 200 OK 및 정상 응답을 반환")
    void findSimilarityListTest() throws Exception {
        //given
        SameAdDto.InspectionAdInfo inspectionAdInfo = SameAdDto.InspectionAdInfo.builder()
            .id("202407A00001")
            .product("상품명_1")
            .advertiser("광고주_1")
            .category("의류")
            .postDate("2024-06-20")
            .content("어쩌구. 저쩌구.")
            .build();

        SameAdDto.AdSimilarityInfo adSimilarityInfo = SameAdDto.AdSimilarityInfo.builder()
            .id("202312A00001")
            .product("상품명_2")
            .advertiser("광고주_2")
            .category("의류")
            .postDate("2023-11-11")
            .similarityPercent(80)
            .sameSentenceCount(7)
            .build();

        SameAdDto.FindSimilarityListResponse response = SameAdDto.FindSimilarityListResponse.builder()
            .inspectionAdInfo(inspectionAdInfo)
            .adSimilarityInfoList(List.of(adSimilarityInfo))
            .build();

        doReturn(response)
            .when(sameAdFacade)
            .findSimilarityList(anyString());

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/same-ad/result/202407A00001")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8));

        // then
        perform.andExpect(status().is(FIND_SIMILARITY_LIST_SUCCESS.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FIND_SIMILARITY_LIST_SUCCESS.getCode()))
            .andExpect(jsonPath("message").value(FIND_SIMILARITY_LIST_SUCCESS.getMessage()));
    }
}