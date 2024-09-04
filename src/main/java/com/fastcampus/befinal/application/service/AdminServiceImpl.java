package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.dataprovider.UserManagementStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserManagementReader userManagementReader;
    private final UserManagementStore userManagementStore;
    private final UserStore userStore;

    @Override
    @Transactional
    public void approveUser(AdminCommand.ApproveUserRequest command) {
        command.userList().stream()
            .map(AdminCommand.ApproveUser::empNo)
            .forEach(empNo -> {
                UserManagement userManagement = userManagementReader.findByEmpNo(empNo);
                userStore.store(userManagement);
                userManagementStore.delete(userManagement);
            });
    }

    @Override
    @Transactional
    public void rejectUser(AdminCommand.RejectUserRequest command) {
        command.userList().stream()
            .map(AdminCommand.RejectUser::empNo)
            .forEach(userManagementStore::deleteByEmpNumber);
    }
}
