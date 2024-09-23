package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DashboardCode implements Code {
    // success
    CHECK_DASHBOARD_SUCCESS(3200),
    CHECK_ADMIN_DASHBOARD_SUCCESS(3201),
    GET_USER_NAME_LIST_SUCCESS(3202);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
