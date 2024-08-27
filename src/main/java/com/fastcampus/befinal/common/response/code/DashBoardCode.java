package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DashBoardCode implements Code {
    CHECK_DASHBOARD(1200);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
