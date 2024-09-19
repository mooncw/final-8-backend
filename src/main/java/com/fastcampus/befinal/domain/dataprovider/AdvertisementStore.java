package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserSummary;

import java.util.List;

public interface AdvertisementStore {
    void updateAssignee(UserSummary userSummary, List<String> personalTaskAdIdList);
}
