package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.dataprovider.UserUnionViewReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("AuthService 테스트")
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserUnionViewReader userUnionViewReader;

    @Mock
    private UserManagementStore userManagementStore;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpTest() {
        //given
        AuthCommand.SignUpRequest command = AuthCommand.SignUpRequest.builder()
            .id("aaaa")
            .name("홍길동")
            .password("aaaaaaa1")
            .phoneNumber("01011112222")
            .empNo("11111111")
            .email("hong@hong.com")
            .build();

        doNothing()
            .when(userManagementStore)
            .store(any(AuthCommand.SignUpRequest.class));

        doReturn(false)
            .when(userUnionViewReader)
            .existsSignUpUser(any(AuthCommand.SignUpRequest.class));

        //when
        authService.signUp(command);

        //verify
        verify(userManagementStore, times(1)).store(any(AuthCommand.SignUpRequest.class));
        verify(userUnionViewReader, times(1)).existsSignUpUser(any(AuthCommand.SignUpRequest.class));
    }

    @Test
    @DisplayName("아이디 중복 확인 성공 테스트")
    void checkIdDuplicationTest() {
        //given
        AuthCommand.CheckIdDuplicationRequest command = AuthCommand.CheckIdDuplicationRequest.builder()
            .id("aaaa")
            .build();

        doReturn(false)
            .when(userUnionViewReader)
            .existsUserId(any(AuthCommand.CheckIdDuplicationRequest.class));

        //when
        authService.checkIdDuplication(command);

        //verify
        verify(userUnionViewReader, times(1)).existsUserId(any(AuthCommand.CheckIdDuplicationRequest.class));
    }
}
