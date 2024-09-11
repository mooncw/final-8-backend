package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AdminCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminSuccessCode implements SuccessCode {
    APPROVE_USER_SUCCESS(HttpStatus.OK, AdminCode.APPROVE_USER_SUCCESS, "회원가입 승인되었습니다."),
    FIND_SIGN_UP_USER_LIST_SUCCESS(HttpStatus.OK, AdminCode.FIND_SIGN_UP_USER_LIST_SUCCESS,
        "회원가입 신청 유저 목록 조회되었습니다."),
    REJECT_USER_SUCCESS(HttpStatus.OK, AdminCode.REJECT_USER_SUCCESS, "회원가입 반려되었습니다."),
    FIND_USER_LIST_SUCCESS(HttpStatus.OK, AdminCode.FIND_USER_LIST_SUCCESS, "회원 정보 목록이 조회되었습니다."),
    DELETE_USER_SUCCESS(HttpStatus.OK, AdminCode.DELETE_USER_SUCCESS, "회원 정보가 삭제되었습니다."),
    FIND_USER_TASK_LIST_SUCCESS(HttpStatus.OK, AdminCode.FIND_USER_TASK_LIST_SUCCESS, "작업자 관리 정보 조회되었습니다."),
    FIND_UNASSIGNED_AD_LIST_SUCCESS(HttpStatus.OK, AdminCode.FIND_UNASSIGNED_AD_LIST_SUCCESS,
        "작업 배분 광고 목록이 조회되었습니다."),
    ASSIGN_TASK_SUCCESS(HttpStatus.OK, AdminCode.ASSIGN_TASK_SUCCESS, "작업 배분이 완료되었습니다.");

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
