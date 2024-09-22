package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SameAdCode implements Code {
    //success
    GET_SAME_ADVERTISEMENT_LIST_SUCCESS(3700),
    FIND_SIMILARITY_LIST_SUCCESS(3701),
    FIND_SIMILARITY_DETAIL_SUCCESS(3702),

    //error
    NOT_FOUND_SAME_ADVERTISEMENT_FOR_INSPECTION_ADVERTISEMENT(3750);

    private final Integer code;

    @Override
    public Integer getCode() { return code;}
}
