package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "마이페이지 관련 API")
public class UserController {
    private final UserFacade userFacade;

    @PutMapping("/info")
    @Operation(summary = "회원정보 변경")
    @ApiResponse(responseCode = "200", description = "회원 정보가 변경되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3300," +
                    "\"message\": \"회원 정보가 변경되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> updateUserInfo(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        UserDto.UserUpdateRequest request,
        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updateUser(userDetails, request);
        return ResponseEntityFactory.toResponseEntity(UPDATE_USER_SUCCESS);
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiResponse(responseCode = "200", description = "비밀번호가 변경되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3301," +
                    "\"message\": \"비밀번호가 변경되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> updatePassword(
        @RequestBody
        @Validated
        UserDto.PasswordUpdateRequest request,
        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updatePassword(userDetails, request);
        return ResponseEntityFactory.toResponseEntity(UPDATE_PASSWORD_SUCCESS);
    }
}
