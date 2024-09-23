package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.UserSummaryReader;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.repository.UserSummaryRepository;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.NOT_FOUND_USER_SUMMARY;

@DataProvider
@RequiredArgsConstructor
public class UserSummaryReaderImpl implements UserSummaryReader {
    private final UserSummaryRepository userSummaryRepository;

    @Override
    public UserSummary findById(Long id) {
        return userSummaryRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SUMMARY));
    }
}
