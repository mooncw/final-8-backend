package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AdminCode implements Code {
    //success
    APPROVE_USER_SUCCESS(1000),
    FIND_SIGN_UP_USER_LIST_SUCCESS(1001),
    REJECT_USER_SUCCESS(1002),
    FIND_USER_LIST_SUCCESS(1003),
    DELETE_USER_SUCCESS(1004),
    FIND_USER_TASK_LIST_SUCCESS(1005),
    FIND_UNASSIGNED_AD_LIST_SUCCESS(1006),
    FIND_ASSIGNEE_LIST_SUCCESS(1007),

    //error
    NOT_FOUND_USER_MANAGEMENT(1050),
    NOT_FOUND_USER_SUMMARY(1051),
    INVALID_USER_TASK_SORT_TYPE(1052);

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
