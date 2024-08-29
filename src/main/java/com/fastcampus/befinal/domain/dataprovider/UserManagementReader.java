package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserManagement;

public interface UserManagementReader {
    UserManagement findByEmpNo(String empNo);
}
