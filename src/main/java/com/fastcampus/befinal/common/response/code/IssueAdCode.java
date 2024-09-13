package com.fastcampus.befinal.common.response.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IssueAdCode implements Code{
    //success
    GET_ADVERTISEMENT_DETAIL_SUCCESS(3400),
    SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS(3401),
    GET_PROVISION_LIST_SUCCESS(3402),
    GET_DECISION_LIST_SUCCESS(3403),
    SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS(3404),
    //error
    NOT_FOUND_ADVERTISEMENT_ID(3450),
    NOT_FOUND_ISSUE_REVIEW_ID(3451),
    NOT_FOUND_PROVISION_ID(3452),
    NOT_FOUND_DECISION_ID(3453),
    INVALID_OPERATION_TYPE(3454);

    private final Integer code;

    @Override
    public Integer getCode() { return code;}
}
