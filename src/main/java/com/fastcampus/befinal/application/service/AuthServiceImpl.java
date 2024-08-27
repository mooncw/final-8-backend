package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public void signUp(AuthCommand.SignUpRequest command) {
        validateSignUpUser(command);
        userManagementStore.store(command);
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

        AuthInfo.CheckIdTokenInfo checkIdTokenInfo = AuthInfo.CheckIdTokenInfo.from(Generator.generateUniqueValue());

        checkTokenStore.store(checkIdTokenInfo);

        return checkIdTokenInfo;
    }

    private void validateUserIdDuplication(AuthCommand.CheckIdDuplicationRequest command) {
        if (userUnionViewReader.existsUserId(command)) {
            throw new BusinessException(USER_ID_ALREADY_EXIST);
        }
    }

    @Override
    public void checkCertificationNumber(AuthCommand.CheckCertificationNumberRequest command) {
        SmsCertification smsCertification = smsCertificationReader.find(command);

        validateCertificationNumber(command, smsCertification);
    }

    @Override
    public void updateCheckList(AuthCommand.UpdateCheckListRequest command) {
//        signUpCheckListStore
    }

    private void validateCertificationNumber(AuthCommand.CheckCertificationNumberRequest command,
                                             SmsCertification smsCertification) {
        if (!Objects.equals(command.certificationNumber(), smsCertification.getCertificationNumber())) {
            throw new BusinessException(INCONSISTENT_CERTIFICATION_NUMBER);
        }
    }
}
