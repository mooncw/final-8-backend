package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.DashBoardCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DashBoardErrorCode implements ErrorCode{
    DENIED_ACCESS_DASHBOARD(HttpStatus.UNAUTHORIZED, DashBoardCode.DENIED_ACCESS_DASHBOARD, "대시보드에 접근 권한이 없습니다.");

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
