package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.AdminCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminErrorCode implements ErrorCode {
    NOT_FOUND_USER_MANAGEMENT(HttpStatus.BAD_REQUEST, AdminCode.NOT_FOUND_USER_MANAGEMENT,
        "회원가입 승인 목록에 없는 유저입니다."),
    NOT_FOUND_USER_SUMMARY(HttpStatus.BAD_REQUEST, AdminCode.NOT_FOUND_USER_SUMMARY, "해당 유저의 요약 정보가 없습니다."),
    INVALID_USER_TASK_SORT_TYPE(HttpStatus.BAD_REQUEST, AdminCode.INVALID_USER_TASK_SORT_TYPE,
        "유효하지 않는 정렬 기준입니다."),
    INSUFFICIENT_UNASSIGNED_ADVERTISEMENT(HttpStatus.BAD_REQUEST, AdminCode.INSUFFICIENT_UNASSIGNED_ADVERTISEMENT,
        "배분 될 광고 수가 부족합니다."),
    INVALID_TASK_ASSIGNMENT_AMOUNT(HttpStatus.BAD_REQUEST, AdminCode.INVALID_TASK_ASSIGNMENT_AMOUNT,
        "적절한 할당 작업량이 아닙니다."),
    NOT_FOUND_USER_ROLE(HttpStatus.BAD_REQUEST, AdminCode.NOT_FOUND_USER_ROLE, "유효하지 않은 권한입니다.");

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
