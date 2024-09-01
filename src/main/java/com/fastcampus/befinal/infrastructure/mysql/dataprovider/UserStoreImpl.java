package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.repository.UserRepository;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import com.fastcampus.befinal.infrastructure.mysql.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final MysqlEntityMapper mysqlEntityMapper;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void store(UserManagement userManagement) {
        userRepository.save(mysqlEntityMapper.from(userManagement));
    }

    @Override
    public void update(UserInfo.UserUpdateInfo userInfo) { userRepository.save(userEntityMapper.from(userInfo)); }
}
