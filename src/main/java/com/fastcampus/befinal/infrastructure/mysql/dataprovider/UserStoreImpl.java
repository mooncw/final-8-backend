package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.repository.UserRepository;
import com.fastcampus.befinal.domain.repository.UserRepositoryCustom;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataProvider
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final MysqlEntityMapper mysqlEntityMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryCustom userRepositoryCustom;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(AdminCommand.SelectedAssigneeInfo info) {
        userRepositoryCustom.updateAdditionalTaskCount(info);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
