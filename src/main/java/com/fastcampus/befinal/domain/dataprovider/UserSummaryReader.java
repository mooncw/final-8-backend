package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.DashboardInfo;

import java.util.List;

public interface UserSummaryReader {
    UserSummary findById(Long id);
    List<DashboardInfo.UserName> findUserNameList();
}
