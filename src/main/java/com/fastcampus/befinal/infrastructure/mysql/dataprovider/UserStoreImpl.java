package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.repository.UserRepository;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import com.fastcampus.befinal.infrastructure.mysql.mapper.UserPasswordMapper;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final MysqlEntityMapper mysqlEntityMapper;
    private final UserPasswordMapper userPasswordMapper;

    @Override
    public void store(UserManagement userManagement) {
        userRepository.save(mysqlEntityMapper.from(userManagement));
    }

    @Override
    public void update(UserInfo.UserUpdateInfo userInfo) { userRepository.updateUserInfoById(userInfo.id(), userInfo.email(), userInfo.phoneNumber()); }

    @Override
    public void update(UserInfo.PasswordUpdateInfo userInfo) {
        String encodePassword = userPasswordMapper.ecodePassword(userInfo.password());
        userRepository.updatePasswordById(userInfo.id(), encodePassword);
    }
}
