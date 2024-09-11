package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FilterCode implements Code {
    // Success
    FILTER_MEDIA_LIST(3600),
    FILTER_CATEGORY_LIST(3601);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
