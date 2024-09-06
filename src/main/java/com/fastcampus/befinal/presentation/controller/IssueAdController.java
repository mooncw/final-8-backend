package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.GET_ADVERTISEMENT_DETAIL_SUCCESS;

@RestController
@RequestMapping("/api/v1/issue-ad")
@RequiredArgsConstructor
public class IssueAdController {
    private final IssueAdFacade issueAdFacade;

    @GetMapping("/result/{advertisementId}")
    public ResponseEntity<AppApiResponse<IssueAdDto.IssueAdDetailResponse>> findIssueAdDetail(
        @PathVariable String advertisementId
    ){
        IssueAdDto.IssueAdDetailResponse response = issueAdFacade.findIssueAdDetail(advertisementId);
        return ResponseEntityFactory.toResponseEntity(GET_ADVERTISEMENT_DETAIL_SUCCESS, response);
    }
}
