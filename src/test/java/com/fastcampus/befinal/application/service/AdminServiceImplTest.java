package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.type.UserTaskSortType;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.AdminInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("AdminService 테스트")
@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private UserManagementReader userManagementReader;

    @Mock
    private UserManagementStore userManagementStore;

    @Mock
    private UserReader userReader;

    @Mock
    private UserStore userStore;

    @Mock
    private UserSummaryReader userSummaryReader;

    @Mock
    private UserSummaryStore userSummaryStore;

    @Mock
    private AdvertisementReader advertisementReader;

    @Test
    @DisplayName("회원가입 승인 성공 테스트")
    void approveUserTest() {
        //given
        AdminCommand.ApproveUser approveUser = AdminCommand.ApproveUser.builder()
            .empNo("11111111")
            .build();

        AdminCommand.ApproveUserRequest command = AdminCommand.ApproveUserRequest.builder()
            .userList(List.of(approveUser))
            .build();

        UserManagement userManagement = UserManagement.builder()
            .userId("aaaa")
            .name("홍길동")
            .password("aaaaaaa1")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now())
            .build();

        doReturn(userManagement)
            .when(userManagementReader)
            .findByEmpNo(anyString());

        doNothing()
            .when(userStore)
            .store(any(UserManagement.class));

        doNothing()
            .when(userManagementStore)
            .delete(any(UserManagement.class));


        //when
        adminService.approveUser(command);

        //verify
        verify(userManagementReader, times(1)).findByEmpNo(anyString());
        verify(userStore, times(1)).store(any(UserManagement.class));
        verify(userManagementStore, times(1)).delete(any(UserManagement.class));
    }

    @Test
    @DisplayName("회원가입 반려 성공 테스트")
    void rejectUserTest() {
        //given
        AdminCommand.RejectUser rejectUser = AdminCommand.RejectUser.builder()
            .empNo("11111111")
            .build();

        AdminCommand.RejectUserRequest command = AdminCommand.RejectUserRequest.builder()
            .userList(List.of(rejectUser))
            .build();

        doNothing()
            .when(userManagementStore)
            .deleteByEmpNumber(anyString());


        //when
        adminService.rejectUser(command);

        //verify
        verify(userManagementStore, times(1)).deleteByEmpNumber(anyString());
    }

    @Test
    @DisplayName("회원가입 신청 유저 목록 조회 성공 테스트")
    void findSignUpUserScrollTest() {
        //given
        Long cursorId = 2L;

        AdminInfo.SignUpUserInfo info = AdminInfo.SignUpUserInfo.builder()
            .id(cursorId)
            .name("홍길동")
            .empNumber("11111111")
            .phoneNumber("01011112222")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(5L))
            .build();

        ScrollPagination<Long, AdminInfo.SignUpUserInfo> doReturnScroll = ScrollPagination.of(1L, cursorId, List.of(info));

        doReturn(doReturnScroll)
            .when(userManagementReader)
            .findScroll(anyLong());

        //when
        ScrollPagination<Long, AdminInfo.SignUpUserInfo> scroll = adminService.findSignUpUserScroll(cursorId);

        //then
        assertThat(scroll.totalElements()).isEqualTo(doReturnScroll.totalElements());
        assertThat(scroll.currentCursorId()).isEqualTo(doReturnScroll.currentCursorId());
        assertThat(scroll.contents()).isEqualTo(doReturnScroll.contents());
    }

    @Test
    @DisplayName("회원 정보 목록 조회 성공 테스트")
    void findUserScrollTest() {
        //given
        Long cursorId = 2L;

        AdminInfo.UserInfo info = AdminInfo.UserInfo.builder()
            .id(cursorId)
            .empNumber("11111111")
            .name("홍길동")
            .role(USER_AUTHORITY)
            .userId("hong")
            .phoneNumber("01011112222")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(5L))
            .finalLoginDateTime(LocalDateTime.now().minusDays(2L))
            .build();

        ScrollPagination<Long, AdminInfo.UserInfo> doReturnScroll = ScrollPagination.of(4L, cursorId, List.of(info));

        doReturn(doReturnScroll)
            .when(userReader)
            .findScroll(anyLong());

        //when
        ScrollPagination<Long, AdminInfo.UserInfo> scroll = adminService.findUserScroll(cursorId);

        //then
        assertThat(scroll.totalElements()).isEqualTo(doReturnScroll.totalElements());
        assertThat(scroll.currentCursorId()).isEqualTo(doReturnScroll.currentCursorId());
        assertThat(scroll.contents()).isEqualTo(doReturnScroll.contents());
    }

    @Test
    @DisplayName("회원 정보 삭제 성공 테스트")
    void deleteUserTest() {
        //given
        Long userId = 6L;

        User user = User.builder()
            .id(userId)
            .userId("hong")
            .name("홍길동")
            .password("asdf1234")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(5))
            .finalLoginDateTime(LocalDateTime.now().minusDays(2))
            .role(USER_AUTHORITY)
            .build();

        UserSummary userSummary = UserSummary.builder()
            .id(user.getId())
            .name(user.getName())
            .build();

        doReturn(user)
            .when(userReader)
            .findById(anyLong());

        doNothing()
            .when(userStore)
            .delete(any(User.class));

        doReturn(userSummary)
            .when(userSummaryReader)
            .findById(anyLong());

        doNothing()
            .when(userSummaryStore)
            .update(any(UserSummary.class));

        //when
        adminService.deleteUser(userId);

        //verify
        verify(userReader, times(1)).findById(anyLong());
        verify(userStore, times(1)).delete(any(User.class));
        verify(userSummaryReader, times(1)).findById(anyLong());
        verify(userSummaryStore, times(1)).update(any(UserSummary.class));
    }

    @Test
    @DisplayName("작업자 관리 정보 조회 성공 테스트")
    void findUserTaskScrollTest() {
        //given
        AdminCommand.FindUserTaskListRequest command = AdminCommand.FindUserTaskListRequest.builder()
            .cursorId(1)
            .sorted(UserTaskSortType.EMP_NUMBER)
            .period("2024-9-1")
            .build();

        AdminInfo.UserTaskInfo info = AdminInfo.UserTaskInfo.builder()
            .id(1L)
            .empNumber("11111111")
            .name("hong")
            .totalAd(300)
            .notDoneAd(75)
            .doneAd(225)
            .doneRatio(0.75)
            .build();

        ScrollPagination<Integer, AdminInfo.UserTaskInfo> doReturnScroll =
            ScrollPagination.of(4L, command.cursorId(), List.of(info));

        doReturn(doReturnScroll)
            .when(userReader)
            .findScroll(any(AdminCommand.FindUserTaskListRequest.class));

        //when
        ScrollPagination<Integer, AdminInfo.UserTaskInfo> scroll = adminService.findUserTaskScroll(command);

        //then
        assertThat(scroll.totalElements()).isEqualTo(doReturnScroll.totalElements());
        assertThat(scroll.currentCursorId()).isEqualTo(doReturnScroll.currentCursorId());
        assertThat(scroll.contents()).isEqualTo(doReturnScroll.contents());
    }

    @Test
    @DisplayName("작업 배분 광고 목록 조회 성공 테스트")
    void findUnassignedAdScrollTest() {
        //given
        String cursorId = "202409A00001";

        AdminInfo.UnassignedAdInfo info = AdminInfo.UnassignedAdInfo.builder()
            .adId("202409A00002")
            .product("호박")
            .advertiser("홍씨네집")
            .category("식품")
            .build();

        ScrollPagination<String, AdminInfo.UnassignedAdInfo> doReturnScroll =
            ScrollPagination.of(4L, cursorId, List.of(info));

        doReturn(doReturnScroll)
            .when(advertisementReader)
            .findUnassignedAdScroll(anyString());

        //when
        ScrollPagination<String, AdminInfo.UnassignedAdInfo> scroll = adminService.findUnassignedAdScroll(cursorId);

        //then
        assertThat(scroll.totalElements()).isEqualTo(doReturnScroll.totalElements());
        assertThat(scroll.currentCursorId()).isEqualTo(doReturnScroll.currentCursorId());
        assertThat(scroll.contents()).isEqualTo(doReturnScroll.contents());
    }
}
