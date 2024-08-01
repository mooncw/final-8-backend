package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExampleCode implements Code {
    //error
    ERROR(0),

    //success
    SUCCESS(1);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
