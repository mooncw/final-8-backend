package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.UserSummaryStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.repository.UserSummaryRepository;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserSummaryStoreImpl implements UserSummaryStore {
    private final UserSummaryRepository userSummaryRepository;
    private final MysqlEntityMapper mysqlEntityMapper;

    @Override
    public void store(UserManagement userManagement) {
        UserSummary userSummary = mysqlEntityMapper.toSummaryUser(userManagement);
        userSummaryRepository.save(userSummary);
    }

    @Override
    public void update(UserSummary userSummary) {
        userSummary.updateDeletedUserInfo();
    }
}
