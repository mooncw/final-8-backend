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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fastcampus.befinal.common.response.success.info.SameAdSuccessCode.GET_SAME_ADVERTISEMENT_LIST_SUCCESS;

@RestController
@RequestMapping("/api/v1/same-ad")
@RequiredArgsConstructor
@Tag(name = "Same-Ad", description = "동일광고 관련 API")
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
                                "adId": "A00001",
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
}
