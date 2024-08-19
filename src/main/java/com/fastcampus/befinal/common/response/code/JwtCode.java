package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtCode implements Code {
    //error
    NOT_VALID_JWT(1050),
    EXPIRED_JWT(1051),
    UNSUPPORTED_JWT(1052),
    ILLEGAL_JWT(1053),
    NOT_EXPIRED_ACCESSTOKEN(1054),
    NOT_FOUND_REFRESHTOKEN(1055),
    INCONSISTENT_REFRESHTOKEN(1056);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
