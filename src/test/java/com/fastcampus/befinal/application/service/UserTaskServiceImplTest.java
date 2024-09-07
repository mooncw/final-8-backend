package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.UserTaskReader;
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

@DisplayName("UserTaskService 테스트")
@ExtendWith(MockitoExtension.class)
class UserTaskServiceImplTest {
    @InjectMocks
    private UserTaskServiceImpl userTaskService;

    @Mock
    private UserTaskReader userTaskReader;

    @Test
    @DisplayName("나의 작업 조회 기능 성공 테스트")
    void loadFilterTaskTest() {
        // given
        String userId = "testID";

        TaskCommand.FilterConditionRequest taskCommand = TaskCommand.FilterConditionRequest.builder()
                .cursorInfo(TaskCommand.CursorInfo.builder()
                        .cursorState(false)
                        .cursorId("A0001")
                        .build())
                .state(false)
                .issue(false)
                .media(List.of("온라인"))
                .category(List.of("IT"))
                .build();

        TaskInfo.AdCountInfo myAdCount = TaskInfo.AdCountInfo.builder()
                .myTotalAd(10)
                .myDoneAd(5)
                .myNotDoneAd(5)
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

        doReturn(myAdCount)
                .when(userTaskReader)
                .findMyAdCount(userId);

        doReturn(filterTaskPagination)
                .when(userTaskReader)
                .findFilterMyAdvertisement(userId, taskCommand);

        // when
        TaskInfo.TaskResponse result = userTaskService.loadFilterTask(userId, taskCommand);

        // then
        assertNotNull(result);
        assertEquals(myAdCount.myTotalAd(), result.adCount().myTotalAd());
        assertEquals(myAdCount.myDoneAd(), result.adCount().myDoneAd());
        assertEquals(myAdCount.myNotDoneAd(), result.adCount().myNotDoneAd());

        assertEquals(filterTaskPagination.totalElements(), result.taskList().totalElements());
        assertEquals(filterTaskPagination.currentCursorId().cursorState(), result.taskList().cursorInfo().cursorState());
        assertEquals(filterTaskPagination.currentCursorId().cursorId(), result.taskList().cursorInfo().cursorId());

        assertEquals(1, result.taskList().advertisementList().size());
        TaskInfo.AdvertisementListInfo firstAd = result.taskList().advertisementList().get(0);
        assertEquals("A0001", firstAd.adId());
        assertEquals("온라인", firstAd.media());
        assertEquals("IT", firstAd.category());
        assertEquals("노트북", firstAd.product());
        assertEquals("테크컴퍼니", firstAd.advertiser());
        assertEquals(false, firstAd.state());
        assertEquals(false, firstAd.issue());

        verify(userTaskReader, times(1)).findMyAdCount(userId);
        verify(userTaskReader, times(1)).findFilterMyAdvertisement(userId, taskCommand);
    }
}