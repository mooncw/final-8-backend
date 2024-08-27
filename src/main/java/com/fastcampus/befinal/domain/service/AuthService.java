package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.info.AuthInfo;

public interface AuthService {
    void signUp(AuthCommand.SignUpRequest command);

    AuthInfo.CheckIdTokenInfo checkIdDuplication(AuthCommand.CheckIdDuplicationRequest command);

    void checkCertificationNumber(AuthCommand.CheckCertificationNumberRequest command);

    void updateCheckList(AuthCommand.UpdateCheckListRequest command);
}
