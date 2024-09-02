package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("UserService 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private CheckTokenStore checkTokenStore;

    @Mock
    private CheckTokenReader checkTokenReader;

    @Mock
    private UserStore userStore;

    @Mock
    private JwtAuthService jwtAuthService;

    @Test
    @DisplayName("회원정보 수정 테스트")
    void updateUserTest() {
        //given
        UserCommand.UserUpdateRequest command = UserCommand.UserUpdateRequest.builder()
            .id("aaaa")
            .email("hong@gil.com")
            .phoneNumber("01011113333")
            .certificationNumberCheckToken("aaaa-aaaa-aaaa")
            .build();

        String jwtToken = "Bearer aaaaaaaa";

        doReturn(true)
            .when(checkTokenReader)
            .exists(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(checkTokenStore)
            .delete(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(userStore)
            .update(any(UserInfo.UserUpdateInfo.class));

        doNothing()
            .when(jwtAuthService)
            .setAuthentication(any(String.class));

        //when
        userService.updateUser(command, jwtToken);

        //verify
        verify(userStore, times(1)).update(any(UserInfo.UserUpdateInfo.class));
        verify(jwtAuthService, times(1)).setAuthentication(any(String.class));
        verify(checkTokenReader, times(1)).exists(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(1)).delete(any(AuthInfo.CheckTokenInfo.class));
    }

    @Test
    @DisplayName("회원정보 수정 테스트 - 전화번호 없이")
    void updateUserTestWithoutPhoneNumber() {
        //given
        UserCommand.UserUpdateRequest command = UserCommand.UserUpdateRequest.builder()
            .id("aaaa")
            .email("hong@gil.com")
            .phoneNumber(null)
            .certificationNumberCheckToken(null)
            .build();

        String jwtToken = "Bearer aaaaaaaa";

        doNothing()
            .when(userStore)
            .update(any(UserInfo.UserUpdateInfo.class));

        doNothing()
            .when(jwtAuthService)
            .setAuthentication(any(String.class));

        //when
        userService.updateUser(command, jwtToken);

        //verify
        verify(userStore, times(1)).update(any(UserInfo.UserUpdateInfo.class));
        verify(jwtAuthService, times(1)).setAuthentication(any(String.class));
    }

}
