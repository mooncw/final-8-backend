package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtTokenCode implements Code {
    //error
    NOT_VALID_JWT_TOKEN(1050),
    EXPIRED_JWT_TOKEN(1051),
    UNSUPPORTED_JWT_TOKEN(1052),
    ILLEGAL_JWT_TOKEN(1053);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
