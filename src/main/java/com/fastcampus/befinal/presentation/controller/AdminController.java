package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AdminFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.AdminDto;
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

import static com.fastcampus.befinal.common.response.success.info.AdminSuccessCode.APPROVE_USER_SUCCESS;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 관련 API")
public class AdminController {
    private final AdminFacade adminFacade;

    @PostMapping("approve-user")
    @Operation(summary = "회원가입 승인")
    @ApiResponse(responseCode = "200", description = "회원가입 승인되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1000, " +
                    "\"message\": \"회원가입 승인되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> approveUser(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.ApproveUserRequest request
    ) {
        adminFacade.approveUser(request);
        return ResponseEntityFactory.toResponseEntity(APPROVE_USER_SUCCESS);
    }
}
