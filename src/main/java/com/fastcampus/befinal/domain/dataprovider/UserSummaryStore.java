package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.entity.UserSummary;

public interface UserSummaryStore {
    void store(UserManagement userManagement);

    void update(UserSummary userSummary);
}
