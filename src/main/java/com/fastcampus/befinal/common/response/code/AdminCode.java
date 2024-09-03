package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AdminCode implements Code {
    //success
    APPROVE_USER_SUCCESS(1000),

    //error
    NOT_FOUND_USER_MANAGEMENT(1050);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
