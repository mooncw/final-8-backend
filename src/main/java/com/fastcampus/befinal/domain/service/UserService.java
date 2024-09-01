package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.UserCommand;

public interface UserService {
    void updateUser(UserCommand.UserUpdateRequest command, String authorizationHeader);
    void updatePassword(UserCommand.PasswordUpdateRequest command, String authorizationHeader);
}
