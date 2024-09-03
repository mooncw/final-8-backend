package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.AuthDtoMapper;
import com.fastcampus.befinal.application.mapper.UserDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.domain.service.AuthService;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.domain.service.SmsService;
import com.fastcampus.befinal.domain.service.UserService;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import com.fastcampus.befinal.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final JwtAuthService jwtAuthService;
    private final UserDtoMapper userDtoMapper;
    private final SmsService smsService;
    private final AuthDtoMapper authDtoMapper;
    private final AuthService authService;

    public void updateUser(UserDetailsInfo user, UserDto.UserUpdateRequest request, String authorizationHeader){
        userService.updateUser(userDtoMapper.toUserCommand(request, user));
        jwtAuthService.setAuthenticationBearer(authorizationHeader);
    }
    public void updatePassword(UserDetailsInfo user, UserDto.PasswordUpdateRequest request, String authorizationHeader){
        userService.updatePassword(userDtoMapper.toUserCommand(request, user.getUser()));
        jwtAuthService.setAuthenticationBearer(authorizationHeader);
    }

    public void sendCertificationNumber(UserDto.SendCertificationNumberRequest request) {
        smsService.sendCertificationNumber(authDtoMapper.toAuthCommand(request));
    }

    public AuthDto.CheckCertificationNumberResponse checkCertificationNumber(UserDto.CheckCertificationNumberRequest request) {
        AuthInfo.CheckCertificationNumberTokenInfo info = authService.checkCertificationNumber(authDtoMapper.toAuthCommand(request));
        return authDtoMapper.from(info);
    }
}
