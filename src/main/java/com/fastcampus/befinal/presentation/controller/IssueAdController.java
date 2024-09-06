package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.IssueAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Issue-Ad", description = "지적광고 관련 API")
public class IssueAdController {
    private final IssueAdFacade issueAdFacade;

    @GetMapping("/result/{advertisementId}")
    @Operation(summary = "지적광고 상세보기")
    @ApiResponse(responseCode = "200", description = "지적 광고 상세 정보가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = IssueAdDto.IssueAdDetailResponse.class,
                example = "{" +
                    "\"code\": 3400, " +
                    "\"message\": \"지적 광고 상세 정보가 조회되었습니다.\", " +
                    "\"data\": {" +
                    "   \"id\": \"202409A00001\"" +
                    "   \"product\": \"상품명\"" +
                    "   \"advertiser\": \"광고주\"" +
                    "   \"category\": \"업종\"" +
                    "   \"postDate\": \"2024-09-01\"" +
                    "   \"assigneeName\": \"담당자\"" +
                    "   \"modifierName\": \"수정자\"" +
                    "   \"content\": \"광고내용\"" +
                    "   \"reviewList\": [" +
                    "     {" +
                    "       \"provisionArticle\": 1" +
                    "       \"provisionContent\": \"조항내용\"" +
                    "       \"sentence\": \"지적문장\"" +
                    "       \"opinion\": \"검토내용\"" +
                    "     }" +
                    "   ]" +
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
}
