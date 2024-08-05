package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthCode implements Code {
    //error
    DENIED_ACCESS(1150);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
