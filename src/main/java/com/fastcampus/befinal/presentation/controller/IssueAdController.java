package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(summary = "지적광고 심의결정 리뷰 저장")
    @ApiResponse(responseCode = "200", description = "지적광고에 대한 검토작업이 저장되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                    "\"code\": 3401, " +
                    "\"message\": \"지적광고에 대한 검토작업이 저장되었습니다.\" " +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> saveIssueAdReviews(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        IssueAdDto.IssueAdReviewRequest requests
    ){
        issueAdFacade.saveIssueAdReviews(requests);
        return ResponseEntityFactory.toResponseEntity(SAVE_ISSUE_ADVERTISEMENT_REVIEW_SUCCESS);
    }

    @GetMapping("/options/provision")
    @Operation(summary = "조항 리스트 불러오기")
    @ApiResponse(responseCode = "200", description = "조항 리스트가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                    "\"code\": 3402, " +
                    "\"message\": \"조항 리스트가 조회되었습니다.\", " +
                    "\"data\": [ " +
                        "{ " +
                            "\"id\": 1, " +
                            "\"article\": 1, " +
                            "\"content\": \"1항 내용\" " +
                        "}," +
                        "{" +
                        "\"id\": 2," +
                        "\"article\": 2," +
                        "\"content\": \"2항 내용\" " +
                        "}" +
                    "]" +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<IssueAdDto.IssueAdProvisionResponse>> findProvisionList(){
        IssueAdDto.IssueAdProvisionResponse responses = issueAdFacade.findProvisionList();
        return ResponseEntityFactory.toResponseEntity(GET_PROVISION_LIST_SUCCESS, responses);
    }

    @GetMapping("/options/decision")
    @Operation(summary = "심의결정 리스트 불러오기")
    @ApiResponse(responseCode = "200", description = "심의결정 리스트가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                    "\"code\": 3403, " +
                    "\"message\": \"심의결정 리스트가 조회되었습니다.\", " +
                    "\"data\": [ " +
                        "{ " +
                        "\"id\": 1, " +
                        "\"decision\": \"해당사항 없음\" " +
                        "}, " +
                        "{" +
                        "\"id\": 2," +
                        "\"decision\": \"주의 및 경고\" " +
                        "}" +
                    "]" +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<IssueAdDto.IssueAdDecisionResponse>> findDecisionList(){
        IssueAdDto.IssueAdDecisionResponse responses = issueAdFacade.findDecisionList();
        return ResponseEntityFactory.toResponseEntity(GET_DECISION_LIST_SUCCESS, responses);
    }

    @PostMapping("/result/decision")
    @Operation(summary = "지적광고 심의결정 완료")
    @ApiResponse(responseCode = "200", description = "심의결정이 저장되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                    "\"code\": 3404, " +
                    "\"message\": \"심의결정이 저장되었습니다.\" " +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> saveIssueAdDecision(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        IssueAdDto.IssueAdResultDecisionRequest request
    ){
        issueAdFacade.saveIssueAdResultDecision(request);
        return ResponseEntityFactory.toResponseEntity(SAVE_ISSUE_ADVERTISEMENT_DECISION_SUCCESS);
    }
}
