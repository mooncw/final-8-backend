package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AdminCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminSuccessCode implements SuccessCode {
    APPROVE_USER_SUCCESS(HttpStatus.OK, AdminCode.APPROVE_USER_SUCCESS, "회원가입 승인되었습니다."),
    FIND_SIGN_UP_USER_LIST_SUCCESS(HttpStatus.OK, AdminCode.FIND_SIGN_UP_USER_LIST_SUCCESS,
        "회원가입 신청 유저 목록 조회되었습니다.");

    private final HttpStatus httpStatus;
    private final Code code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Integer getCode() {
        return code.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
