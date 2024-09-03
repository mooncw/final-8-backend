package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

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
            .id("aaaa")
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
}
