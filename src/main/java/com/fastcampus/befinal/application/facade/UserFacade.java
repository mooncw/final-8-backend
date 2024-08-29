package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.UserDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.service.UserService;
import com.fastcampus.befinal.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Facade
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public void updateUser(UserDetails user, UserDto.UserUpdateRequest request){
        userService.updateUser(userDtoMapper.toUserCommand(request));
    }
    public void updatePassword(UserDetails user, UserDto.PasswordUpdateRequest request){}
}
