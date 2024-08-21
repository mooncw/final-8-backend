package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AuthFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.presentation.dto.AuthDto;
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

import static com.fastcampus.befinal.common.contant.AuthConstant.SWAGGER_REISSUE_RESPONSE_ACCESSTOKEN;
import static com.fastcampus.befinal.common.contant.AuthConstant.SWAGGER_REISSUE_RESPONSE_REFRESHTOKEN;
import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.REISSUE_JWT_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.SIGNUP_SUCCESS;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/signup")
    public ResponseEntity<AppApiResponse> signUp(
        @RequestBody
        @Validated
        AuthDto.SignUpRequest request
    ) {
        authFacade.signUp(request);
        return ResponseEntityFactory.toResponseEntity(SIGNUP_SUCCESS);
    }

    @PostMapping("/id-check")
    public void checkId(
        @RequestBody
        @Validated
        AuthDto.CheckIdDuplicationRequest request
    ) {
//        authFacade.checkIdDuplication(request);
//        return ResponseEntityFactory.toResponseEntity(CHECK_ID_DUPLICATION_SUCCESS);
    }

    @PostMapping("/reissue")
    @Operation(summary = "JWT 토큰 재발급")
    @ApiResponse(responseCode = "200", description = "JWT 재발급되었습니다.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{ " +
                                    "\"code\": 1100, " +
                                    "\"message\": \"JWT 재발급되었습니다.\", " +
                                    "\"data\": { " +
                                        "\"accessToken\": \"" + SWAGGER_REISSUE_RESPONSE_ACCESSTOKEN + "\", " +
                                        "\"refreshToken\": \"" + SWAGGER_REISSUE_RESPONSE_REFRESHTOKEN + "\" " +
                                    "} " +
                                "}"
                )
            )
    )
    public ResponseEntity<AppApiResponse<AuthDto.ReissueJwtResponse>> reissueJwt(
        @RequestBody
        @Validated
        AuthDto.ReissueJwtRequest request
    ) {
        AuthDto.ReissueJwtResponse response = authFacade.reissueJwt(request);
        return ResponseEntityFactory.toResponseEntity(REISSUE_JWT_SUCCESS, response);
    }
}
