package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.AuthDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.service.JwtCreationService;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AuthFacade {
    private final JwtCreationService jwtCreationService;
    private final AuthDtoMapper authDtoMapper;

    public void signIn(AuthDto.SignInRequest request) {
        jwtCreationService.createJwt(authDtoMapper.toCreateJwtCommand(request));
    }

    public AuthDto.ReissueJwtResponse reissueJwt(AuthDto.ReissueJwtRequest request) {
        AuthInfo.JwtInfo jwtInfo =  jwtCreationService.reissueJwt(authDtoMapper.toReissueJwtCommand(request));
        return authDtoMapper.from(jwtInfo);
    }
}
