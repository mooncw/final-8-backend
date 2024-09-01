package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.UserCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserSuccessCode implements SuccessCode {
    UPDATE_USER_SUCCESS(HttpStatus.OK, UserCode.UPDATE_USER_SUCCESS, "유저 정보가 변경되었습니다."),
    UPDATE_PASSWORD_SUCCESS(HttpStatus.OK, UserCode.UPDATE_PASSWORD_SUCCESS, "비밀번호가 변경되었습니다.");

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
