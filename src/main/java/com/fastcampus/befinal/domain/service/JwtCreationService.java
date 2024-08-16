package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.info.JwtInfo;

public interface JwtCreationService {
    JwtInfo.TokenInfo createJwt(JwtCommand.CreateJwtRequest command);

    JwtInfo.TokenInfo reissueJwt(JwtCommand.ReissueJwtRequest command);
}
