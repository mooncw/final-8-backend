package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원정보 수정 테스트")
    void updateUserTest() {
        //given
        User user = User.builder()
            .userId("aaaa")
            .build();

        UserCommand.UserUpdateRequest command = UserCommand.UserUpdateRequest.builder()
            .user(user)
            .email("hong@gil.com")
            .phoneNumber("01011113333")
            .certNoCheckToken("aaaa-aaaa-aaaa")
            .build();


        doReturn(true)
            .when(checkTokenReader)
            .exists(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(checkTokenStore)
            .delete(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(userStore)
            .update(any(UserInfo.UserUpdateInfo.class));

        //when
        userService.updateUser(command);

        //verify
        verify(userStore, times(1)).update(any(UserInfo.UserUpdateInfo.class));
        verify(checkTokenReader, times(1)).exists(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(1)).delete(any(AuthInfo.CheckTokenInfo.class));
    }

    @Test
    @DisplayName("회원정보 수정 테스트 - 전화번호 없이")
    void updateUserTestWithoutPhoneNumber() {
        //given
        User user = User.builder()
            .userId("aaaa")
            .build();

        UserCommand.UserUpdateRequest command = UserCommand.UserUpdateRequest.builder()
            .user(user)
            .email("hong@gil.com")
            .phoneNumber(null)
            .certNoCheckToken(null)
            .build();

        doNothing()
            .when(userStore)
            .update(any(UserInfo.UserUpdateInfo.class));

        //when
        userService.updateUser(command);

        //verify
        verify(userStore, times(1)).update(any(UserInfo.UserUpdateInfo.class));
    }

    @Test
    @DisplayName("비밀번호 수정 테스트")
    void updatePasswordTest() {
        //given
        String password = "aaaaaaa1";
        User user = User.builder()
            .userId("aaaa")
            .password(password)
            .build();

        UserCommand.PasswordUpdateRequest command = UserCommand.PasswordUpdateRequest.builder()
            .user(user)
            .currentPassword(password)
            .newPassword("aaaaaa2")
            .build();

        doReturn(true)
            .when(passwordEncoder)
            .matches(any(String.class), any(String.class));

        doNothing()
            .when(userStore)
            .update(any(UserInfo.PasswordUpdateInfo.class));

        //when
        userService.updatePassword(command);

        //verify
        verify(passwordEncoder, times(1)).matches(any(String.class), any(String.class));
        verify(userStore, times(1)).update(any(UserInfo.PasswordUpdateInfo.class));
    }

}
