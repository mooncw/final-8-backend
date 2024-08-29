package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.UserManagement;

public interface UserManagementStore {
    void store(AuthCommand.SignUpRequest command);

    void delete(UserManagement userManagement);
}
