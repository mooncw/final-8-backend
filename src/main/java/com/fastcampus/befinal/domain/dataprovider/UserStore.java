package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.UserInfo;

public interface UserStore {
    void store(UserManagement userManagement);

    void update(UserInfo.UserUpdateInfo userInfo);

    void update(UserInfo.PasswordUpdateInfo userInfo);

    void update(AdminCommand.SelectedAssigneeInfo info);

    void delete(User user);
}
