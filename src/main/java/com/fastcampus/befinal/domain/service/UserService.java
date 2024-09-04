package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;

public interface UserService {
    void updateUser(UserCommand.UserUpdateRequest command);
    void updatePassword(UserCommand.PasswordUpdateRequest command);
}
