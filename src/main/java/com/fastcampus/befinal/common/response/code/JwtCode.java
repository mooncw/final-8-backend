package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtCode implements Code {
    //error
    NOT_VALID_JWT(3050),
    EXPIRED_JWT(3051),
    UNSUPPORTED_JWT(3052),
    ILLEGAL_JWT(3053),
    NOT_EXPIRED_ACCESSTOKEN(3054),
    NOT_FOUND_REFRESHTOKEN(3055),
    INCONSISTENT_REFRESHTOKEN(3056);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
