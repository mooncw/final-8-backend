package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthCode implements Code {
    //success
    REISSUE_JWT_SUCCESS(1100),
    SIGNUP_SUCCESS(1101),
    CHECK_ID_DUPLICATION_SUCCESS(1102),
    SEND_CERTIFICATION_NUMBER_SUCCESS(1103),

    //error
    DENIED_ACCESS(1150),
    SIGNUP_USER_ALREADY_EXIST(1151),
    USER_ID_ALREADY_EXIST(1152),
    PHONE_NUMBER_ALREADY_EXIST(1153),
    INCONSISTENT_CERTIFICATION_NUMBER(1155);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
