package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseCode implements Code {
    //error
    SERVER_ERROR(500);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
