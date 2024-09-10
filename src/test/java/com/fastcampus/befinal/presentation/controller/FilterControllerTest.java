package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.FilterFacade;
import com.fastcampus.befinal.common.config.SecurityConfig;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.presentation.dto.FilterDto;
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
import static com.fastcampus.befinal.common.response.success.info.FilterSuccessCode.FILTER_CATEGORY_LIST;
import static com.fastcampus.befinal.common.response.success.info.FilterSuccessCode.FILTER_MEDIA_LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("FilterController 테스트")
@WebMvcTest(FilterController.class)
@Import(SecurityConfig.class)
class FilterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilterFacade filterFacade;

    @MockBean
    private JwtAuthService jwtAuthService;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Test
    @DisplayName("Filter 매체 리스트 조회 시, 200 OK와 정상 응답을 반환")
    void searchMediaOptions() throws Exception {
        // given
        List<FilterDto.FilterOptionResponse> responseList = List.of(
            new FilterDto.FilterOptionResponse("동아일보", 8L),
            new FilterDto.FilterOptionResponse("조선일보", 6L)
        );

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

        doReturn(responseList)
            .when(filterFacade)
            .searchMediaOptions(any(FilterDto.ConditionRequest.class));

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/filter-options/media")
            .param("keyword", "일보")
            .param("period", "2024-8-2")
            .with(user(userDetailsInfo))
            .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is(FILTER_MEDIA_LIST.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FILTER_MEDIA_LIST.getCode()))
            .andExpect(jsonPath("message").value(FILTER_MEDIA_LIST.getMessage()))
            .andExpect(jsonPath("$.data[0].name").value("동아일보"))
            .andExpect(jsonPath("$.data[0].adCount").value(8L));
    }

    @Test
    @DisplayName("Filter 업종 리스트 조회 시, 200 OK와 정상 응답을 반환")
    void searchCategoryOptions() throws Exception {
        // given
        List<FilterDto.FilterOptionResponse> responseList = List.of(
            new FilterDto.FilterOptionResponse("가정용품", 8L),
            new FilterDto.FilterOptionResponse("의료용품", 6L)
        );

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

        doReturn(responseList)
            .when(filterFacade)
            .searchCategoryOptions(any(FilterDto.ConditionRequest.class));

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/filter-options/category")
            .param("keyword", "용품")
            .param("period", "2024-8-2")
            .with(user(userDetailsInfo))
            .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is(FILTER_CATEGORY_LIST.getHttpStatus().value()))
            .andExpect(jsonPath("code").value(FILTER_CATEGORY_LIST.getCode()))
            .andExpect(jsonPath("message").value(FILTER_CATEGORY_LIST.getMessage()))
            .andExpect(jsonPath("$.data[0].name").value("가정용품"))
            .andExpect(jsonPath("$.data[0].adCount").value(8L));
    }
}