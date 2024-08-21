package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthCode implements Code {
    //success
    REISSUE_JWT_SUCCESS(1100),
    SIGNUP_SUCCESS(1101),

    //error
    DENIED_ACCESS(1150),
    SIGNUP_USER_ALREADY_EXIST(1151);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
