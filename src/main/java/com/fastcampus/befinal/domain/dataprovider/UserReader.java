package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AdminInfo;

public interface UserReader {
    User findUser(String userId);

    ScrollPagination<Long, AdminInfo.UserInfo> findScroll(Long cursorId);

    User findById(Long id);

    ScrollPagination<Integer, AdminInfo.UserTaskInfo> findScroll(AdminCommand.FindUserTaskListRequest request);

    User findByPhoneNumber(String phoneNumber);
}
