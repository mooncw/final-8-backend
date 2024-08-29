package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.repository.UserManagementRepository;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.NOT_FOUND_USER_MANAGEMENT;

@DataProvider
@RequiredArgsConstructor
public class UserManagementReaderImpl implements UserManagementReader {
    private final UserManagementRepository userManagementRepository;

    @Override
    public UserManagement findByEmpNo(String empNo) {
        return userManagementRepository.findByEmpNumber(empNo)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_MANAGEMENT));
    }
}
