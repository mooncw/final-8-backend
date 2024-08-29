package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AuthFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
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
import static com.fastcampus.befinal.common.response.success.info.AuthSuccessCode.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1101, " +
                    "\"message\": \"회원가입되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> signUp(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AuthDto.SignUpRequest request
    ) {
        authFacade.signUp(request);
        return ResponseEntityFactory.toResponseEntity(SIGN_UP_SUCCESS);
    }

    @PostMapping("/check-id")
    @Operation(summary = "ID 중복 확인")
    @ApiResponse(responseCode = "200", description = "중복되지 않는 ID입니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                        "\"code\": 1102, " +
                        "\"message\": \"중복되지 않는 ID입니다.\", " +
                        "\"data\": {" +
                            "\"idCheckToken\": \"dd50d3d8-d542-434b-b447-c50fa6ec06e4\"" +
                        "}" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AuthDto.CheckIdDuplicationResponse>> checkIdDuplication(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AuthDto.CheckIdDuplicationRequest request
    ) {
        AuthDto.CheckIdDuplicationResponse response = authFacade.checkIdDuplication(request);
        return ResponseEntityFactory.toResponseEntity(CHECK_ID_DUPLICATION_SUCCESS, response);
    }

    @PostMapping("/cert-no")
    @Operation(summary = "인증 번호 전송 - type: \"SignUp\"")
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
        AuthDto.SendCertificationNumberRequest request
    ) {
        authFacade.sendCertificationNumber(request);
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
        AuthDto.CheckCertificationNumberRequest request
    ) {
        AuthDto.CheckCertificationNumberResponse response = authFacade.checkCertificationNumber(request);
        return ResponseEntityFactory.toResponseEntity(CHECK_CERTIFICATION_NUMBER_SUCCESS, response);
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
        @Validated(DefaultGroupSequence.class)
        AuthDto.ReissueJwtRequest request
    ) {
        AuthDto.ReissueJwtResponse response = authFacade.reissueJwt(request);
        return ResponseEntityFactory.toResponseEntity(REISSUE_JWT_SUCCESS, response);
    }
}
