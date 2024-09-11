package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fastcampus.befinal.common.response.success.info.IssueAdSuccessCode.*;

@RestController
@RequestMapping("/api/v1/issue-ad")
@RequiredArgsConstructor
@Tag(name = "Issue-Ad", description = "지적광고 관련 API")
public class IssueAdController {
    private final IssueAdFacade issueAdFacade;

    @GetMapping("/result/{advertisementId}")
    @Operation(summary = "지적광고 상세보기")
    @ApiResponse(responseCode = "200", description = "지적 광고 상세 정보가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                    "\"code\": 3400, " +
                    "\"message\": \"지적 광고 상세 정보가 조회되었습니다.\", " +
                    "\"data\": {" +
                        "\"id\": \"202409A00001\"," +
                        "\"product\": \"상품명\"," +
                        "\"advertiser\": \"광고주\"," +
                        "\"category\": \"업종\"," +
                        "\"postDate\": \"2024-09-01\"," +
                        "\"assigneeName\": \"담당자\"," +
                        "\"modifierName\": \"수정자\"," +
                        "\"content\": \"광고내용\"," +
                        "\"reviewList\": [" +
                            "{" +
                                "\"provisionArticle\": 1," +
                                "\"provisionContent\": \"조항내용\"," +
                                "\"sentence\": \"지적문장\"," +
                                "\"opinion\": \"검토내용\"" +
                            "}" +
                        "]" +
                    "}" +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<IssueAdDto.IssueAdDetailResponse>> findIssueAdDetail(
        @PathVariable String advertisementId
    ){
        IssueAdDto.IssueAdDetailResponse response = issueAdFacade.findIssueAdDetail(advertisementId);
        return ResponseEntityFactory.toResponseEntity(GET_ADVERTISEMENT_DETAIL_SUCCESS, response);
    }

    @PostMapping("/save-task")
    public ResponseEntity<AppApiResponse> saveIssueAdReviews(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        List<IssueAdDto.IssueAdReviewRequest> requests
    ){
        issueAdFacade.saveIssueAdReviews(requests);
        return ResponseEntityFactory.toResponseEntity(SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS);
    }

    @GetMapping("/options/provision")
    public ResponseEntity<AppApiResponse<List<IssueAdDto.IssueAdProvisionResponse>>> findProvisionList(){
        List<IssueAdDto.IssueAdProvisionResponse> responses = issueAdFacade.findProvisionList();
        return ResponseEntityFactory.toResponseEntity(GET_PROVISION_LIST_SUCCESS, responses);
    }

    @GetMapping("/options/decision")
    public ResponseEntity<AppApiResponse<List<IssueAdDto.IssueAdDecisionResponse>>> findDecisionList(){
        List<IssueAdDto.IssueAdDecisionResponse> responses = issueAdFacade.findDecisionList();
        return ResponseEntityFactory.toResponseEntity(GET_DECISION_LIST_SUCCESS, responses);
    }

    @PostMapping("/result/decision")
    public ResponseEntity<AppApiResponse> saveIssueAdDecision(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        IssueAdDto.IssueAdResultDecisionRequest request
    ){
        issueAdFacade.saveIssueAdResultDecision(request);
        return ResponseEntityFactory.toResponseEntity(SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS);
    }
}
