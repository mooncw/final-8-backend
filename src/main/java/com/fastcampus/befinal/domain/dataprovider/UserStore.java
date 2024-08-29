package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserManagement;

public interface UserStore {
    void store(UserManagement userManagement);
}
