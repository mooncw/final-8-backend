package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.type.CertificationType;
import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Mock
    private SmsCertificationReader smsCertificationReader;

    @Mock
    private CheckTokenStore checkTokenStore;

    @Mock
    private CheckTokenReader checkTokenReader;

    @Mock
    private UserReader userReader;
    
    @Mock
    private UserStore userStore;

    @Mock
    private Generator generator;

    @Spy
    private PasswordEncoder passwordEncoder = Mockito.spy(BCryptPasswordEncoder.class);;

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
            .idCheckToken("aaaa-aaaa-aaaa")
            .certificationNumberCheckToken("bbbb-bbbb-bbbb")
            .build();

        doReturn(true)
            .when(checkTokenReader)
            .exists(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(checkTokenStore)
            .delete(any(AuthInfo.CheckTokenInfo.class));

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
        verify(checkTokenReader, times(2)).exists(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(2)).delete(any(AuthInfo.CheckTokenInfo.class));
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

        doNothing()
            .when(checkTokenStore)
            .store(any(AuthInfo.CheckTokenInfo.class));

        //when
        authService.checkIdDuplication(command);

        //verify
        verify(userUnionViewReader, times(1)).existsUserId(any(AuthCommand.CheckIdDuplicationRequest.class));
        verify(checkTokenStore, times(1)).store(any(AuthInfo.CheckTokenInfo.class));
    }

    @Test
    @DisplayName("인증 번호 확인 성공 테스트")
    void checkCertificationNumberTest() {
        //given
        AuthCommand.CheckCertificationNumberRequest command = AuthCommand.CheckCertificationNumberRequest.builder()
            .type(CertificationType.SIGN_UP)
            .phoneNumber("01011112222")
            .certificationNumber("111111")
            .build();

        SmsCertification smsCertification = SmsCertification.builder()
            .certificationNumber("111111")
            .build();

        doReturn(smsCertification)
            .when(smsCertificationReader)
            .find(any(AuthCommand.CheckCertificationNumberRequest.class));

        doNothing()
            .when(checkTokenStore)
            .store(any(AuthInfo.CheckTokenInfo.class));

        //when
        authService.checkCertificationNumber(command);

        //verify
        verify(smsCertificationReader, times(1)).find(any(AuthCommand.CheckCertificationNumberRequest.class));
        verify(checkTokenStore, times(1)).store(any(AuthInfo.CheckTokenInfo.class));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void signInTest() {
        //given
        AuthCommand.SignInRequest command = AuthCommand.SignInRequest.builder()
            .id("aaaa")
            .password("asdf1234")
            .build();

        User user = User.builder()
            .userId("aaaa")
            .name("홍길동")
            .password(passwordEncoder.encode(command.password()))
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(USER_AUTHORITY)
            .build();

        doReturn(user)
            .when(userReader)
            .findUser(anyString());

        doReturn(true)
            .when(passwordEncoder)
            .matches(anyString(), anyString());

        //when
        AuthInfo.UserInfo userInfo = authService.signIn(command);

        //then
        assertThat(userInfo.id()).isEqualTo(user.getUserId());
        assertThat(userInfo.name()).isEqualTo(user.getName());
        assertThat(userInfo.phoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userInfo.empNo()).isEqualTo(user.getEmpNumber());
        assertThat(userInfo.email()).isEqualTo(user.getEmail());
        assertThat(userInfo.role()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("아이디 찾기 성공 테스트")
    void findIdTest() {
        // given
        AuthCommand.FindIdRequest request = AuthCommand.FindIdRequest.builder()
            .name("홍길동")
            .phoneNumber("01011112222")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        User user = User.builder()
            .userId("aaaa")
            .name("홍길동")
            .password("aaaa1111")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(USER_AUTHORITY)
            .build();

        doReturn(user)
            .when(userReader)
            .findByPhoneNumber(anyString());

        doReturn(true)
            .when(checkTokenReader)
            .exists(any(AuthInfo.CheckTokenInfo.class));

        // when
        AuthInfo.FindIdInfo idInfo = authService.findId(request);

        // then
        assertThat(idInfo.userId()).isEqualTo(user.getUserId());

        verify(userReader, times(1)).findByPhoneNumber(anyString());
        verify(checkTokenReader, times(1)).exists(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(1)).delete(any(AuthInfo.CheckTokenInfo.class));
    }

    @Test
    @DisplayName("비밀번호 찾기 성공 테스트")
    void findPasswordTest() {
        // given
        AuthCommand.FindPasswordRequest request = AuthCommand.FindPasswordRequest.builder()
            .userId("aaaa")
            .name("홍길동")
            .phoneNumber("01011112222")
            .certNoCheckToken("ca1.cb1.cc1")
            .build();

        User user = User.builder()
            .userId("aaaa")
            .name("홍길동")
            .password("aaaa1111")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(USER_AUTHORITY)
            .build();

        doReturn(user)
            .when(userReader)
            .findByUserIdAndNameAndPhoneNumber(any(AuthCommand.FindPasswordRequest.class));

        doReturn(true)
            .when(checkTokenReader)
            .exists(any(AuthInfo.CheckTokenInfo.class));

        doNothing()
            .when(checkTokenStore)
            .store(any(AuthInfo.CheckTokenInfo.class), any(String.class));

        // when
        authService.findPassword(request);

        // then
        verify(userReader, times(1)).findByUserIdAndNameAndPhoneNumber(any(AuthCommand.FindPasswordRequest.class));
        verify(checkTokenReader, times(1)).exists(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(1)).delete(any(AuthInfo.CheckTokenInfo.class));
        verify(checkTokenStore, times(1)).store(any(AuthInfo.CheckTokenInfo.class), any(String.class));
    }

    @Test
    @DisplayName("비밀번호 수정 성공 테스트")
    void editPasswordTest() {
        // given
        String userId = "testtest";

        AuthCommand.EditPasswordRequest request = AuthCommand.EditPasswordRequest.builder()
            .password("1111aaaa")
            .passwordResetToken("pa1.pb1.pc1")
            .build();

        User user = User.builder()
            .userId(userId)
            .name("홍길동")
            .password("aaaa1111")
            .phoneNumber("01011112222")
            .empNumber("11111111")
            .email("hong@hong.com")
            .signUpDateTime(LocalDateTime.now().minusDays(10))
            .finalLoginDateTime(LocalDateTime.now().minusDays(5))
            .role(USER_AUTHORITY)
            .build();

        doReturn(userId)
            .when(checkTokenReader)
            .findUserIdByResetToken(any(AuthInfo.CheckTokenInfo.class));

        doReturn(user)
            .when(userReader)
            .findUser(any(String.class));

        doNothing()
            .when(userStore)
            .update(any(UserInfo.PasswordUpdateInfo.class));

        doNothing()
            .when(checkTokenStore)
            .delete(any(AuthInfo.CheckTokenInfo.class));

        // when
        authService.editPassword(request);

        // then
        verify(checkTokenReader, times(1)).findUserIdByResetToken(any(AuthInfo.CheckTokenInfo.class));
        verify(userReader, times(1)).findUser(any(String.class));
        verify(userStore, times(1)).update(any(UserInfo.PasswordUpdateInfo.class));
        verify(checkTokenStore, times(1)).delete(any(AuthInfo.CheckTokenInfo.class));
    }
}
