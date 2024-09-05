package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.AdminInfo;

public interface UserManagementReader {
    UserManagement findByEmpNo(String empNo);

    ScrollPagination<Long, AdminInfo.SignUpUserInfo> findScroll(Long id);
}
