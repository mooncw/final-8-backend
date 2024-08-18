package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestCode implements Code {
    //error
    REQUEST_ERROR(400);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}