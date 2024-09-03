package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.AuthDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.JwtInfo;
import com.fastcampus.befinal.domain.service.AuthService;
import com.fastcampus.befinal.domain.service.JwtCreationService;
import com.fastcampus.befinal.domain.service.SmsService;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AuthFacade {
    private final AuthService authService;
    private final JwtCreationService jwtCreationService;
    private final SmsService smsService;
    private final AuthDtoMapper authDtoMapper;

    public void signUp(AuthDto.SignUpRequest request) {
        authService.signUp(authDtoMapper.toAuthCommand(request));
    }

    public AuthDto.CheckIdDuplicationResponse checkIdDuplication(AuthDto.CheckIdDuplicationRequest request) {
        AuthInfo.CheckIdTokenInfo info = authService.checkIdDuplication(authDtoMapper.toAuthCommand(request));
        return authDtoMapper.from(info);
    }

    public void sendCertificationNumber(AuthDto.SendCertificationNumberRequest request) {
        smsService.sendCertificationNumber(authDtoMapper.toAuthCommand(request));
    }

    public AuthDto.CheckCertificationNumberResponse checkCertificationNumber(AuthDto.CheckCertificationNumberRequest request) {
        AuthInfo.CheckCertificationNumberTokenInfo info = authService.checkCertificationNumber(authDtoMapper.toAuthCommand(request));
        return authDtoMapper.from(info);
    }

    public AuthDto.SignInResponse signIn(AuthDto.SignInRequest request) {
        AuthInfo.UserInfo userInfo = authService.signIn(authDtoMapper.toAuthCommand(request));
        JwtInfo.TokenInfo tokenInfo = jwtCreationService.createJwt(authDtoMapper.toJwtCommand(request));
        return authDtoMapper.of(userInfo, tokenInfo);
    }

    public AuthDto.ReissueJwtResponse reissueJwt(AuthDto.ReissueJwtRequest request) {
        JwtInfo.TokenInfo jwtInfo =  jwtCreationService.reissueJwt(authDtoMapper.toJwtCommand(request));
        return authDtoMapper.from(jwtInfo);
    }
}
