package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.repository.UserRepository;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import com.fastcampus.befinal.infrastructure.mysql.mapper.UserPasswordMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataProvider
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final MysqlEntityMapper mysqlEntityMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void store(UserManagement userManagement) {
        userRepository.save(mysqlEntityMapper.from(userManagement));
    }

    @Override
    public void update(UserInfo.UserUpdateInfo userInfo) {
        User currentUser = entityManager.merge(userInfo.user());
        currentUser.updateEmailAndPhoneNumber(userInfo.email(), userInfo.phoneNumber());
    }

    @Override
    public void update(UserInfo.PasswordUpdateInfo userInfo) {
        User currentUser = entityManager.merge(userInfo.user());
        currentUser.updatePassword(passwordEncoder.encode(userInfo.password()));
    }
}
