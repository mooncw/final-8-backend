package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserCode implements Code{
    UPDATE_USER_SUCCESS(3300),
    UPDATE_PASSWORD_SUCCESS(3301),

    INVALID_CURRENT_PASSWORD(3350);

    private final Integer code;

    @Override
    public Integer getCode() { return code;}
}
