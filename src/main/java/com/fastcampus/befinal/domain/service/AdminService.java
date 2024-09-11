package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;

public interface AdminService {
    void approveUser(AdminCommand.ApproveUserRequest command);

    void rejectUser(AdminCommand.RejectUserRequest command);

    ScrollPagination<Long, AdminInfo.SignUpUserInfo> findSignUpUserScroll(Long cursorId);

    ScrollPagination<Long, AdminInfo.UserInfo> findUserScroll(Long cursorId);

    void deleteUser(Long userId);

    ScrollPagination<Integer, AdminInfo.UserTaskInfo> findUserTaskScroll(AdminCommand.FindUserTaskListRequest command);

    ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId);
}
