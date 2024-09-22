package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DashboardCode implements Code {
    // success
    CHECK_DASHBOARD_SUCCESS(3200),
    CHECK_ADMIN_DASHBOARD_SUCCESS(3201);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
