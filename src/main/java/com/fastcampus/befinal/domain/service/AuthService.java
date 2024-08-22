package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.AuthCommand;

public interface AuthService {
    void signUp(AuthCommand.SignUpRequest command);

    void checkIdDuplication(AuthCommand.CheckIdDuplicationRequest command);
}
