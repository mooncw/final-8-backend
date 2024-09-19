package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserManagementReader userManagementReader;
    private final UserManagementStore userManagementStore;
    private final UserReader userReader;
    private final UserStore userStore;
    private final UserSummaryReader userSummaryReader;
    private final UserSummaryStore userSummaryStore;
    private final AdvertisementReader advertisementReader;

    @Override
    @Transactional
    public void approveUser(AdminCommand.ApproveUserRequest command) {
        command.userList().stream()
            .map(AdminCommand.ApproveUser::empNo)
            .forEach(empNo -> {
                UserManagement userManagement = userManagementReader.findByEmpNo(empNo);
                userStore.store(userManagement);
                userSummaryStore.store(userManagement);
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

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Long, AdminInfo.SignUpUserInfo> findSignUpUserScroll(Long cursorId) {
        return userManagementReader.findScroll(cursorId);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Long, AdminInfo.UserInfo> findUserScroll(Long cursorId) {
        return userReader.findScroll(cursorId);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userReader.findById(userId);

        userStore.delete(user);

        UserSummary userSummary = userSummaryReader.findById(userId);

        userSummaryStore.update(userSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Integer, AdminInfo.UserTaskInfo> findUserTaskScroll(AdminCommand.FindUserTaskListRequest command) {
        return userReader.findScroll(command);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId) {
        return advertisementReader.findUnassignedAdScroll(cursorId);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminInfo.AssigneeListInfo findAssigneeList() {
        return userReader.findAllAssignee();
    }
}
