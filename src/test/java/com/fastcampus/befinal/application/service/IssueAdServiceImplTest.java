package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.AdReviewReader;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
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

@DisplayName("IssueAdService 테스트")
@ExtendWith(MockitoExtension.class)
public class IssueAdServiceImplTest {
    @InjectMocks
    private IssueAdServiceImpl issueAdService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Mock
    private AdReviewReader adReviewReader;

    @Test
    @DisplayName("지적광고 조회 성공 테스트")
    void findIssueAdListTest() {
        //given
        TaskCommand.FilterConditionRequest command = TaskCommand.FilterConditionRequest.builder()
            .cursorInfo(TaskCommand.CursorInfo.builder()
                .cursorState(false)
                .cursorId("A0001")
                .build())
            .state(false)
            .issue(false)
            .media(List.of("온라인"))
            .category(List.of("IT"))
            .build();

        TaskInfo.AdvertisementListInfo adInfo = TaskInfo.AdvertisementListInfo.builder()
            .adId("A0001")
            .media("온라인")
            .category("IT")
            .product("노트북")
            .advertiser("테크컴퍼니")
            .state(false)
            .issue(false)
            .build();

        List<TaskInfo.AdvertisementListInfo> adList = List.of(adInfo);

        ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> filterTaskPagination =
            ScrollPagination.of(
                10L,
                TaskInfo.CursorInfo.builder()
                    .cursorState(false)
                    .cursorId("A0001")
                    .build(),
                adList
            );

        doReturn(filterTaskPagination)
            .when(advertisementReader)
            .findIssueAdList(command);

        //when
        TaskInfo.TaskListInfo request = issueAdService.findIssueAdList(command);

        //then
        assertNotNull(request);

        assertEquals(filterTaskPagination.totalElements(), request.totalElements());
        assertEquals(filterTaskPagination.currentCursorId().cursorState(), request.cursorInfo().cursorState());
        assertEquals(filterTaskPagination.currentCursorId().cursorId(), request.cursorInfo().cursorId());

        assertEquals(1, request.advertisementList().size());
        TaskInfo.AdvertisementListInfo firstAd = request.advertisementList().getFirst();
        assertEquals("A0001", firstAd.adId());
        assertEquals("온라인", firstAd.media());
        assertEquals("IT", firstAd.category());
        assertEquals("노트북", firstAd.product());
        assertEquals("테크컴퍼니", firstAd.advertiser());
        assertEquals(false, firstAd.state());
        assertEquals(false, firstAd.issue());

        verify(advertisementReader, times(1)).findIssueAdList(command);
    }

    @Test
    @DisplayName("지적광고 상세조회 성공 테스트")
    void getIssueAdDetailTest(){
        //given
        String advertisementId ="A0001";
        IssueAdInfo.IssueAdDetailInfo detailInfo = IssueAdInfo.IssueAdDetailInfo.builder().build();
        List<IssueAdInfo.IssueAdReviewInfo> reviewInfoList = List.of(IssueAdInfo.IssueAdReviewInfo.builder().build());

        doReturn(detailInfo)
            .when(advertisementReader)
            .findIssueAdDetail(advertisementId);

        doReturn(reviewInfoList)
            .when(adReviewReader)
            .findIssueAdReviewList(advertisementId);

        //when
        issueAdService.findIssueAdDetail(advertisementId);

        //verify
        verify(advertisementReader, times(1)).findIssueAdDetail(advertisementId);
        verify(adReviewReader, times(1)).findIssueAdReviewList(advertisementId);

    }
}
