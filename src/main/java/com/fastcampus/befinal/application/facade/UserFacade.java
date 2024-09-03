package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.UserDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.domain.service.UserService;
import com.fastcampus.befinal.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public void updateUser(UserDetailsInfo user, UserDto.UserUpdateRequest request){
        userService.updateUser(userDtoMapper.toUserCommand(request, user));
    }
    public void updatePassword(UserDetailsInfo user, UserDto.PasswordUpdateRequest request){
        userService.updatePassword(userDtoMapper.toUserCommand(request, user.getUser()));
    }
}
