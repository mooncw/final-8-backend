package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.SameAdFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.*;

@RestController
@RequestMapping("/api/v1/same-ad")
@RequiredArgsConstructor
@Tag(name = "Same-Ad", description = "동일 광고 관련 API")
public class SameAdController {
    private final SameAdFacade sameAdFacade;

    @PostMapping
    @Operation(summary = "동일 광고 리스트 조회")
    @ApiResponse(responseCode = "200", description = "동일 광고 리스트가 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                {
                    "code": 3700,
                    "message": "동일 광고 리스트가 조회되었습니다.",
                    "data": {
                        "totalElements": 1,
                        "cursorId": "202409A00001",
                        "sameAdvertisementList" : [
                            {
                                "adId": "202409A00001",
                                "media": "동아일보",
                                "category": "가정용품",
                                "product": "상품_202409A00001",
                                "advertiser": "광고주_810",
                                "same" : true
                            }
                        ]
                    }
                }
                """
            )
        )
    )
    public ResponseEntity<AppApiResponse<SameAdDto.SameTaskListInfo>> findSameAdList(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        SameAdDto.SameAdFilterConditionRequest request
    ){
        SameAdDto.SameTaskListInfo response = sameAdFacade.findSameAdList(request);
        return ResponseEntityFactory.toResponseEntity(GET_SAME_ADVERTISEMENT_LIST_SUCCESS, response);
    }

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
                                "id": "202409N00001",
                                "product": "명작수",
                                "advertiser": "아모레퍼시픽 코리아",
                                "category": "식품음료",
                                "postDate": "2024-09-11",
                                "content": "어쩌구. 저쩌구."
                            },
                            "adSimilarityInfoList": [
                                {
                                    "id": "202409A00058",
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

    @GetMapping("/result/{inspectionAdvertisementId}/detail/{comparisonAdvertisementId}")
    @Operation(summary = "동일 광고 유사율 상세화면 조회")
    @ApiResponse(responseCode = "200", description = "동일 광고 유사율 상세화면이 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 3702,
                        "message": "동일 광고 유사율 상세화면이 조회되었습니다.",
                        "data": {
                            "content": "어쩌구. 저쩌구.",
                            "sameSentence": "어쩌구."
                        }
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<SameAdDto.FindSimilarityDetailResponse>> findSimilarityDetail(
        @PathVariable
        String inspectionAdvertisementId,

        @PathVariable
        String comparisonAdvertisementId
    ) {
        SameAdDto.FindSimilarityDetailResponse response =
            sameAdFacade.findSimilarityDetail(inspectionAdvertisementId, comparisonAdvertisementId);
        return ResponseEntityFactory.toResponseEntity(FIND_SIMILARITY_DETAIL_SUCCESS, response);
    }
}
