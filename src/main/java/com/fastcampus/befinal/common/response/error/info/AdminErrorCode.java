package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.AdminCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminErrorCode implements ErrorCode {
    NOT_FOUND_USER_MANAGEMENT(HttpStatus.BAD_REQUEST, AdminCode.NOT_FOUND_USER_MANAGEMENT,
        "회원가입 승인 목록에 없는 유저입니다.");

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
