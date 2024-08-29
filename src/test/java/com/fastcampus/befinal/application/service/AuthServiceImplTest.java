package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.type.CertificationType;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.info.AuthInfo;
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

    @Mock
    private SmsCertificationReader smsCertificationReader;

    @Mock
    private CheckTokenStore checkTokenStore;

    @Mock
    private CheckTokenReader checkTokenReader;

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
}
