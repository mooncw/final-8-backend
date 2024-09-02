package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthCode implements Code {
    //success
    REISSUE_JWT_SUCCESS(3100),
    SIGN_UP_SUCCESS(3101),
    CHECK_ID_DUPLICATION_SUCCESS(3102),
    SEND_CERTIFICATION_NUMBER_SUCCESS(3103),
    CHECK_CERTIFICATION_NUMBER_SUCCESS(3104),

    //error
    DENIED_ACCESS(3150),
    SIGNUP_USER_ALREADY_EXIST(3151),
    USER_ID_ALREADY_EXIST(3152),
    PHONE_NUMBER_ALREADY_EXIST(3153),
    NOT_FOUND_CERTIFICATION_NUMBER(3154),
    INCONSISTENT_CERTIFICATION_NUMBER(3155),
    INVALID_CERTIFICATION_TYPE(3156),
    INVALID_ID_CHECK_TOKEN(3157),
    INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN(3158),
    NOT_FOUND_USER(3159);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
