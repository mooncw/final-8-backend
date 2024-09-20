package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.SameAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
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

import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.FIND_SIMILARITY_LIST_SUCCESS;

@RestController
@RequestMapping("/api/v1/same-ad")
@RequiredArgsConstructor
@Tag(name = "Same-Ad", description = "동일 광고 관련 API")
public class SameAdController {
    private final SameAdFacade sameAdFacade;

    @GetMapping("/result/{inspectionAdvertisementId}")
    @Operation(summary = "동일 광고 유사율 리스트 조회")
    @ApiResponse(responseCode = "200", description = "동일 광고 유사율 리스트가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 3701,
                        "message": "동일 광고 유사율 리스트가 조회되었습니다.",
                        "data": {
                            "inspectionAdInfo": {
                                "id": "202409N00001
                                "product": "명작수",
                                "advertiser": "아모레퍼시픽 코리아",
                                "category": "식품음료",
                                "postDate": "2024-09-11",
                                "content": "어쩌구. 저쩌구."
                            }
                            "sameAdList": [
                                {
                                    "id": "202409A00058
                                    "product": "명작수",
                                    "advertiser": "아모레퍼시픽 코리아",
                                    "category": "식품음료",
                                    "postDate": "2024-08-27",
                                    "similarityPercent": 80,
                                    "sameSentenceCount": 7
                                }
                            ]
                        }
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<SameAdDto.FindSimilarityListResponse>> findSimilarityList(
        @PathVariable
        String inspectionAdvertisementId
    ) {
        SameAdDto.FindSimilarityListResponse response = sameAdFacade.findSimilarityList(inspectionAdvertisementId);
        return ResponseEntityFactory.toResponseEntity(FIND_SIMILARITY_LIST_SUCCESS, response);
    }
}
