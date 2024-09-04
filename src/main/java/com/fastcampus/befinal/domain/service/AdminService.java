package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;

public interface AdminService {
    void approveUser(AdminCommand.ApproveUserRequest command);

    void rejectUser(AdminCommand.RejectUserRequest command);

    ScrollPagination<Long, AdminInfo.SignUpUserInfo> findSignUpUserScroll(Long cursorId);
}
