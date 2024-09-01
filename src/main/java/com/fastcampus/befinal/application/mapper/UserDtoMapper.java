package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.presentation.dto.UserDto;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserDtoMapper {
    UserCommand.UserUpdateRequest toUserCommand(UserDto.UserUpdateRequest request, String id);
    UserCommand.PasswordUpdateRequest toUserCommand(UserDto.PasswordUpdateRequest request, User user);

}
