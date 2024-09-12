package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.info.AuthInfo;

public interface AuthService {
    void signUp(AuthCommand.SignUpRequest command);

    AuthInfo.UserInfo signIn(AuthCommand.SignInRequest command);

    AuthInfo.CheckIdTokenInfo checkIdDuplication(AuthCommand.CheckIdDuplicationRequest command);

    AuthInfo.CheckCertificationNumberTokenInfo checkCertificationNumber(AuthCommand.CheckCertificationNumberRequest command);

    AuthInfo.FindIdInfo findId(AuthCommand.FindIdRequest command);

    AuthInfo.PasswordResetTokenInfo findPassword(AuthCommand.FindPasswordRequest command);
}
