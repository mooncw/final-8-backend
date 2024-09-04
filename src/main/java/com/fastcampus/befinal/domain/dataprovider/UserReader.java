package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AdminInfo;

public interface UserReader {
    User findUser(String userId);

    ScrollPagination<Long, AdminInfo.UserInfo> findScroll(Long cursorId);
}
