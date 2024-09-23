package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.DashboardCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DashboardSuccessCode implements SuccessCode {
    CHECK_DASHBOARD_SUCCESS(HttpStatus.OK, DashboardCode.CHECK_DASHBOARD_SUCCESS, "대시보드 확인 가능합니다."),
    CHECK_ADMIN_DASHBOARD_SUCCESS(HttpStatus.OK, DashboardCode.CHECK_ADMIN_DASHBOARD_SUCCESS, "관리자 대시보드 확인 가능합니다."),
    GET_USER_NAME_LIST_SUCCESS(HttpStatus.OK, DashboardCode.GET_USER_NAME_LIST_SUCCESS, "유저 네임 리스트 조회가 완료되었습니다.");

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
