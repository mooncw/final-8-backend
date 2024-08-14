package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.info.AuthInfo;

public interface JwtCreationService {
    AuthInfo.JwtInfo createJwt(AuthCommand.CreateJwtRequest command);

    AuthInfo.JwtInfo reissueJwt(AuthCommand.ReissueJwtRequest command);
}
