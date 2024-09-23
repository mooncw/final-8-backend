package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserSummary;

public interface UserSummaryReader {
    UserSummary findById(Long id);
}
