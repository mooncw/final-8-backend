package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;

public interface UserManagementStore {
    void store(AuthCommand.SignUpRequest command);
}
