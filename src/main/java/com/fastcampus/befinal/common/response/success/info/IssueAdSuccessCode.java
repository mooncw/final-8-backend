package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.IssueAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum IssueAdSuccessCode implements SuccessCode{
    GET_ADVERTISEMENT_DETAIL_SUCCESS(HttpStatus.OK, IssueAdCode.GET_ADVERTISEMENT_DETAIL_SUCCESS, "지적 광고 상세 정보가 조회되었습니다."),
    GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS(HttpStatus.OK, IssueAdCode.GET_ISSUE_ADVERTISEMENT_LIST_SUCCESS, "지적 광고 리스트가 조회되었습니다.");

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
