package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SameAdCode implements Code {
    //success
    FIND_SIMILARITY_LIST_SUCCESS(3701);

    private final Integer code;

    @Override
    public Integer getCode() { return code;}
}
