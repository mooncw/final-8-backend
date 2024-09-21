package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SameAdCode implements Code {
    //success
    FIND_SIMILARITY_LIST_SUCCESS(3701),
    FIND_SIMILARITY_DETAIL_SUCCESS(3702);

    private final Integer code;

    @Override
    public Integer getCode() { return code;}
}
