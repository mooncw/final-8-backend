package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DashboardCode implements Code {
    // success
    CHECK_DASHBOARD_SUCCESS(1200);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
