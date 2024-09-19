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

import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;
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
                    "\"code\": 3101, " +
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
                        "\"code\": 3102, " +
                        "\"message\": \"중복되지 않는 ID입니다.\", " +
                        "\"data\": {" +
                            "\"idCheckToken\": \"" + SWAGGER_USER_ID_CHECK_TOKEN + "\"" +
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
    @Operation(summary = "인증 번호 전송 - type: \"SignUp\" or \"UpdateUser\" or \"FindId\" or \"FindPassword\"")
    @ApiResponse(responseCode = "200", description = "인증번호 요청 완료되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3103, " +
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
                    "\"code\": 3104, " +
                    "\"message\": \"유효한 인증번호입니다.\", " +
                    "\"data\": {" +
                    "\"certNoCheckToken\": \"" + SWAGGER_CERTIFICATION_NUMBER_CHECK_TOKEN + "\"" +
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

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1105, " +
                    "\"message\": \"로그인되었습니다.\", " +
                    "\"data\": { " +
                        "\"userInfo\": { " +
                            "\"id\": \"" + SWAGGER_USER_ID + "\", " +
                            "\"name\": \"" + SWAGGER_USER_NAME + "\", " +
                            "\"phoneNumber\": \"" + SWAGGER_PHONE_NUMBER + "\", " +
                            "\"empNo\": \"" + SWAGGER_USER_EMP_NUMBER + "\", " +
                            "\"email\": \"" + SWAGGER_USER_EMAIL + "\", " +
                            "\"authority\": \"" + SWAGGER_USER_AUTHORITY_NAME + "\"" +
                        "}, " +
                        "\"tokenInfo\": { " +
                            "\"accessToken\": \"" + SWAGGER_ACCESSTOKEN + "\", " +
                            "\"refreshToken\": \"" + SWAGGER_REFRESHTOKEN + "\" " +
                        "}" +
                    "} " +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AuthDto.SignInResponse>> signIn(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AuthDto.SignInRequest request
    ) {
        AuthDto.SignInResponse response = authFacade.signIn(request);
        return ResponseEntityFactory.toResponseEntity(SIGN_IN_SUCCESS, response);
    }

    @PostMapping("/reissue")
    @Operation(summary = "JWT 토큰 재발급")
    @ApiResponse(responseCode = "200", description = "JWT 재발급되었습니다.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{ " +
                                    "\"code\": 3100, " +
                                    "\"message\": \"JWT 재발급되었습니다.\", " +
                                    "\"data\": { " +
                                        "\"accessToken\": \"" + SWAGGER_ACCESSTOKEN + "\", " +
                                        "\"refreshToken\": \"" + SWAGGER_REFRESHTOKEN + "\" " +
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

    @PostMapping("/find-id")
    @Operation(summary = "회원 아이디 찾기")
    @ApiResponse(responseCode = "200", description = "아이디 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                                "\"code\": 3106, " +
                                "\"message\": \"회원님의 아이디가 조회되었습니다.\", " +
                                "\"data\": {" +
                                    "\"id\": \"" + SWAGGER_USER_ID + "\"" +
                                "}" +
                            "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AuthDto.FindIdResponse>> findUserId(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AuthDto.FindIdRequest request
    ) {
        AuthDto.FindIdResponse response = authFacade.findId(request);
        return ResponseEntityFactory.toResponseEntity(FIND_ID_SUCCESS, response);
    }

    @PostMapping("/find-password")
    @Operation(summary = "회원 비밀번호 찾기 - 인증")
    @ApiResponse(responseCode = "200", description = "비밀번호 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{" +
                                "\"code\": 3107, " +
                                "\"message\": \"회원님의 비밀번호가 조회되었습니다.\", " +
                                "\"data\": {" +
                                    "\"passwordResetToken\": \"" + SWAGGER_CERTIFICATION_NUMBER_CHECK_TOKEN + "\"" +
                                "}" +
                            "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AuthDto.PasswordResetTokenResponse>> findUserPassword(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AuthDto.FindPasswordRequest request
    ) {
        AuthDto.PasswordResetTokenResponse response = authFacade.findPassword(request);
        return ResponseEntityFactory.toResponseEntity(FIND_PASSWORD_SUCCESS, response);
    }
}
