package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.repository.UserManagementRepository;
import com.fastcampus.befinal.domain.repository.UserManagementRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.NOT_FOUND_USER_MANAGEMENT;

@DataProvider
@RequiredArgsConstructor
public class UserManagementReaderImpl implements UserManagementReader {
    private final UserManagementRepository userManagementRepository;
    private final UserManagementRepositoryCustom userManagementRepositoryCustom;

    @Override
    public UserManagement findByEmpNo(String empNo) {
        return userManagementRepository.findByEmpNumber(empNo)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_MANAGEMENT));
    }

    @Override
    public ScrollPagination<Long, AdminInfo.SignUpUserInfo> findScrollById(Long cursorId) {
        return userManagementRepositoryCustom.findScrollById(cursorId);
    }
}
