package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.UserSummaryReader;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.repository.UserSummaryRepository;
import com.fastcampus.befinal.domain.repository.UserSummaryRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.NOT_FOUND_USER_SUMMARY;

@DataProvider
@RequiredArgsConstructor
public class UserSummaryReaderImpl implements UserSummaryReader {
    private final UserSummaryRepository userSummaryRepository;
    private final UserSummaryRepositoryCustom userSummaryRepositoryCustom;

    @Override
    public UserSummary findById(Long id) {
        return userSummaryRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SUMMARY));
    }

    public List<DashboardInfo.UserName> findUserNameList() {
        return userSummaryRepositoryCustom.findUserNameList();
    }
}
