package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;
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

@DisplayName("SameAdService 테스트")
@ExtendWith(MockitoExtension.class)
public class SameAdServiceImplTest {
    @InjectMocks
    private SameAdServiceImpl sameAdService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Test
    @DisplayName("동일광고 조회 성공 테스트")
    void findSameAdListTest() {
        // given
        SameAdCommand.SameAdFilterConditionRequest command = SameAdCommand.SameAdFilterConditionRequest.builder()
            .cursorId("202409A00001")
            .same(true)
            .media(List.of("온라인"))
            .category(List.of("IT"))
            .build();

        SameAdInfo.SameAdvertisementListInfo adInfo = SameAdInfo.SameAdvertisementListInfo.builder()
            .adId("A0001")
            .media("온라인")
            .category("IT")
            .product("노트북")
            .advertiser("테크컴퍼니")
            .same(true)
            .build();

        List<SameAdInfo.SameAdvertisementListInfo> adList = List.of(adInfo);

        ScrollPagination<String, SameAdInfo.SameAdvertisementListInfo> filterTaskPagination =
            ScrollPagination.of(
                10L,
                "202409A0001",
                adList
            );

        doReturn(filterTaskPagination)
            .when(advertisementReader)
            .findSameAdList(command);

        // when
        SameAdInfo.SameTaskListInfo request = sameAdService.findSameAdList(command);

        // then
        assertNotNull(request);

        assertEquals(filterTaskPagination.totalElements(), request.totalElements());
        assertEquals(filterTaskPagination.currentCursorId(), request.cursorId());

        assertEquals(1, request.sameAdvertisementList().size());
        SameAdInfo.SameAdvertisementListInfo firstAd = request.sameAdvertisementList().getFirst();
        assertEquals("A0001", firstAd.adId());
        assertEquals("온라인", firstAd.media());
        assertEquals("IT", firstAd.category());
        assertEquals("노트북", firstAd.product());
        assertEquals("테크컴퍼니", firstAd.advertiser());
        assertEquals(true, firstAd.same());

        verify(advertisementReader, times(1)).findSameAdList(command);
    }
}
