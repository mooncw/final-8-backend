package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.info.DashboardInfo;

import java.util.List;

public interface UserReader {
    User findUser(String userId);

    ScrollPagination<Long, AdminInfo.UserInfo> findScroll(Long cursorId);

    User findById(Long id);

    ScrollPagination<Integer, AdminInfo.UserTaskInfo> findScroll(AdminCommand.FindUserTaskListRequest request);

    User findByPhoneNumber(String phoneNumber);

    AdminInfo.AssigneeListInfo findAllAssignee();

    User findByUserIdAndNameAndPhoneNumber(AuthCommand.FindPasswordRequest command);

    AdminInfo.UserDetailInfo findUserDetailInfo(Long id);

    List<DashboardInfo.UserName> findUserNameList();
}
