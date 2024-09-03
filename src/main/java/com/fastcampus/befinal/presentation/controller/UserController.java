package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.AuthDto;
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

import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.CHECK_CERTIFICATION_NUMBER_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.SEND_CERTIFICATION_NUMBER_SUCCESS;
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
        UserDetailsInfo userDetails,
        @RequestHeader("Authorization")
        String authorizationHeader
    ){
        userFacade.updateUser(userDetails, request, authorizationHeader);
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
        UserDetailsInfo userDetails,
        @RequestHeader("Authorization")
        String authorizationHeader
    ){
        userFacade.updatePassword(userDetails, request, authorizationHeader);
        return ResponseEntityFactory.toResponseEntity(UPDATE_PASSWORD_SUCCESS);
    }

    @PostMapping("/cert-no")
    @Operation(summary = "인증 번호 전송 - type: \"UpdateUser\"")
    @ApiResponse(responseCode = "200", description = "인증번호 요청 완료되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1103, " +
                    "\"message\": \"인증번호 요청 완료되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> sendCertificationNumber(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        UserDto.SendCertificationNumberRequest request
    ) {
        userFacade.sendCertificationNumber(request);
        return ResponseEntityFactory.toResponseEntity(SEND_CERTIFICATION_NUMBER_SUCCESS);
    }

    @PostMapping("/check-cert-no")
    @Operation(summary = "인증 번호 확인")
    @ApiResponse(responseCode = "200", description = "유효한 인증번호입니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1104, " +
                    "\"message\": \"유효한 인증번호입니다.\", " +
                    "\"data\": {" +
                    "\"certNoCheckToken\": \"95f43709-d81e-4a53-9633-249078713923\"" +
                    "}" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AuthDto.CheckCertificationNumberResponse>> checkCertificationNumber(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        UserDto.CheckCertificationNumberRequest request
    ) {
        AuthDto.CheckCertificationNumberResponse response = userFacade.checkCertificationNumber(request);
        return ResponseEntityFactory.toResponseEntity(CHECK_CERTIFICATION_NUMBER_SUCCESS, response);
    }
}
