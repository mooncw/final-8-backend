package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.repository.UserManagementRepository;
import com.fastcampus.befinal.infrastructure.mysql.mapper.MysqlEntityMapper;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserManagementStoreImpl implements UserManagementStore {
    private final UserManagementRepository userManagementRepository;
    private final MysqlEntityMapper mysqlEntityMapper;

    @Override
    public void store(AuthCommand.SignUpRequest command) {
        UserManagement userManagement = mysqlEntityMapper.from(command);
        userManagementRepository.save(userManagement);
    }

    @Override
    public void delete(UserManagement userManagement) {
        userManagementRepository.delete(userManagement);
    }

    @Override
    public void deleteByEmpNumber(String empNumber) {
        userManagementRepository.deleteByEmpNumber(empNumber);
    }
}
