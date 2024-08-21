package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;

public interface UserUnionViewReader {
    boolean existsSignUpUser(AuthCommand.SignUpRequest command);

    boolean existsUserId(AuthCommand.CheckIdDuplicationRequest command);
}
