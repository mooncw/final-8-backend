package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DashBoardCode implements Code {
    // success
    CHECK_DASHBOARD_SUCCESS(1200),

    // error
    DENIED_ACCESS_DASHBOARD(1250);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
