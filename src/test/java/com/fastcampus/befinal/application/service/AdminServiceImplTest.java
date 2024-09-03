package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.AdminInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

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
    private UserStore userStore;

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

        ScrollPagination<AdminInfo.SignUpUserInfo> doReturnScroll = ScrollPagination.of(1L, cursorId, List.of(info));

        doReturn(doReturnScroll)
            .when(userManagementReader)
            .findScrollById(anyLong());

        //when
        ScrollPagination<AdminInfo.SignUpUserInfo> scroll = adminService.findSignUpUserScroll(cursorId);

        //then
        assertThat(scroll.totalElements()).isEqualTo(doReturnScroll.totalElements());
        assertThat(scroll.currentCursorId()).isEqualTo(doReturnScroll.currentCursorId());
        assertThat(scroll.contents()).isEqualTo(doReturnScroll.contents());
    }
}
