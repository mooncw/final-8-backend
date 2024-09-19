package com.fastcampus.befinal.common.response.success.info;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.IssueAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum IssueAdSuccessCode implements SuccessCode{
    GET_ADVERTISEMENT_DETAIL_SUCCESS(HttpStatus.OK, IssueAdCode.GET_ADVERTISEMENT_DETAIL_SUCCESS, "지적 광고 상세 정보가 조회되었습니다."),
    SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS(HttpStatus.OK, IssueAdCode.SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS,"지적광고에 대한 검토작업이 저장되었습니다."),
    GET_PROVISION_LIST_SUCCESS(HttpStatus.OK, IssueAdCode.GET_PROVISION_LIST_SUCCESS, "조항 리스트가 조회되었습니다."),
    GET_DECISION_LIST_SUCCESS(HttpStatus.OK, IssueAdCode.GET_DECISION_LIST_SUCCESS, "심의결정 리스트가 조회되었습니다."),
    SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS(HttpStatus.OK, IssueAdCode.SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS,"심의결정이 저장되었습니다.");

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
