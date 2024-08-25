package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.command.SmsCommand;

public interface UserUnionViewReader {
    boolean existsSignUpUser(AuthCommand.SignUpRequest command);

    boolean existsUserId(AuthCommand.CheckIdDuplicationRequest command);

    boolean existsUserPhoneNumber(SmsCommand.SendCertificationNumberRequest command);
}
