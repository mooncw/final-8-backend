package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserUnionViewReader userUnionViewReader;
    private final UserManagementStore userManagementStore;
    private final SmsCertificationReader smsCertificationReader;
    private final CheckTokenStore checkTokenStore;
    private final CheckTokenReader checkTokenReader;
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(AuthCommand.SignUpRequest command) {
        AuthInfo.CheckTokenInfo idCheckTokenInfo = AuthInfo.CheckTokenInfo.from(command.idCheckToken());
        AuthInfo.CheckTokenInfo certificationNumberCheckTokenInfo = AuthInfo.CheckTokenInfo.from(command.certificationNumberCheckToken());

        validateCheckToken(idCheckTokenInfo, certificationNumberCheckTokenInfo);

        validateSignUpUser(command);

        userManagementStore.store(command);
    }

    private void validateCheckToken(AuthInfo.CheckTokenInfo idCheckTokenInfo, AuthInfo.CheckTokenInfo certificationNumberCheckTokenInfo) {
        if (!checkTokenReader.exists(idCheckTokenInfo)) {
            throw new BusinessException(INVALID_ID_CHECK_TOKEN);
        }

        if (!checkTokenReader.exists(certificationNumberCheckTokenInfo)) {
            throw new BusinessException(INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN);
        }

        checkTokenStore.delete(idCheckTokenInfo);
        checkTokenStore.delete(certificationNumberCheckTokenInfo);
    }

    private void validateSignUpUser(AuthCommand.SignUpRequest command) {
        if (userUnionViewReader.existsSignUpUser(command)) {
            throw new BusinessException(SIGNUP_USER_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthInfo.CheckIdTokenInfo checkIdDuplication(AuthCommand.CheckIdDuplicationRequest command) {
        validateUserIdDuplication(command);

        AuthInfo.CheckTokenInfo checkTokenInfo = AuthInfo.CheckTokenInfo.from(Generator.generateUniqueValue());

        checkTokenStore.store(checkTokenInfo);

        return AuthInfo.CheckIdTokenInfo.from(checkTokenInfo.token());
    }

    private void validateUserIdDuplication(AuthCommand.CheckIdDuplicationRequest command) {
        if (userUnionViewReader.existsUserId(command)) {
            throw new BusinessException(USER_ID_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthInfo.CheckCertificationNumberTokenInfo checkCertificationNumber(AuthCommand.CheckCertificationNumberRequest command) {
        SmsCertification smsCertification = smsCertificationReader.find(command);

        validateCertificationNumber(command, smsCertification);

        AuthInfo.CheckTokenInfo checkTokenInfo = AuthInfo.CheckTokenInfo.from(Generator.generateUniqueValue());

        checkTokenStore.store(checkTokenInfo);

        return AuthInfo.CheckCertificationNumberTokenInfo.from(checkTokenInfo.token());
    }

    private void validateCertificationNumber(AuthCommand.CheckCertificationNumberRequest command,
                                             SmsCertification smsCertification) {
        if (!Objects.equals(command.certificationNumber(), smsCertification.getCertificationNumber())) {
            throw new BusinessException(INCONSISTENT_CERTIFICATION_NUMBER);
        }
    }

    @Override
    @Transactional
    public AuthInfo.UserInfo signIn(AuthCommand.SignInRequest command) {
        User user = validateUserIdAndPassword(command);

        user.updateFinalLoginDateTime();

        return AuthInfo.UserInfo.from(user);
    }

    private User validateUserIdAndPassword(AuthCommand.SignInRequest command) {
        User user = userReader.findUser(command.id());

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new BusinessException(INCONSISTENT_USER_PASSWORD);
        }

        return user;
    }
}
