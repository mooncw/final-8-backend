package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MyTaskCode implements Code{
    // success
    CHECK_MY_TASK_SUCCESS(3500);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
