package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.dataprovider.UserUnionViewReader;
import com.fastcampus.befinal.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.SIGNUP_USER_ALREADY_EXIST;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.USER_ID_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserUnionViewReader userUnionViewReader;
    private final UserManagementStore userManagementStore;

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
    public void checkIdDuplication(AuthCommand.CheckIdDuplicationRequest command) {
        validateUserIdDuplication(command);
    }

    private void validateUserIdDuplication(AuthCommand.CheckIdDuplicationRequest command) {
        if (userUnionViewReader.existsUserId(command)) {
            throw new BusinessException(USER_ID_ALREADY_EXIST);
        }
    }

    @Override
    public void checkCertificationNumber(AuthCommand.CheckCertificationNumberRequest command) {

    }
}
