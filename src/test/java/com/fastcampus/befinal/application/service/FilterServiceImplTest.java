package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.FilterCommand;
import com.fastcampus.befinal.domain.dataprovider.FilterReader;
import com.fastcampus.befinal.domain.info.FilterInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("FilterService 테스트")
@ExtendWith(MockitoExtension.class)
class FilterServiceImplTest {
    @InjectMocks
    private FilterServiceImpl filterService;

    @Mock
    private FilterReader filterReader;

    @Test
    @DisplayName("Filter 매체 리스트 조회 성공 테스트")
    void searchMediaOptions() {
        // given
        FilterCommand.ConditionCommand command = FilterCommand.ConditionCommand.builder()
            .keyword("일보")
            .period("2024-08-2")
            .build();

        List<FilterInfo.FilterOptionInfo> responseList = List.of(
            new FilterInfo.FilterOptionInfo("동아일보", 8L),
            new FilterInfo.FilterOptionInfo("조선일보", 6L)
        );

        doReturn(responseList)
            .when(filterReader)
            .findMediaList(command);

        // when
        List<FilterInfo.FilterOptionInfo> result = filterService.searchMediaOptions(command);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("동아일보", result.get(0).name());
        assertEquals(8L, result.get(0).adCount());
        assertEquals("조선일보", result.get(1).name());
        assertEquals(6L, result.get(1).adCount());

        verify(filterReader, times(1)).findMediaList(command);
    }

    @Test
    @DisplayName("Filter 업종 리스트 조회 성공 테스트")
    void searchCategoryOptions() {
        // given
        FilterCommand.ConditionCommand command = FilterCommand.ConditionCommand.builder()
            .keyword("용품")
            .period("2024-08-2")
            .build();

        List<FilterInfo.FilterOptionInfo> responseList = List.of(
            new FilterInfo.FilterOptionInfo("가정용품", 8L),
            new FilterInfo.FilterOptionInfo("의료용품", 6L)
        );

        doReturn(responseList)
            .when(filterReader)
            .findCategoryList(command);

        // when
        List<FilterInfo.FilterOptionInfo> result = filterService.searchCategoryOptions(command);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("가정용품", result.get(0).name());
        assertEquals(8L, result.get(0).adCount());
        assertEquals("의료용품", result.get(1).name());
        assertEquals(6L, result.get(1).adCount());

        verify(filterReader, times(1)).findCategoryList(command);
    }
}