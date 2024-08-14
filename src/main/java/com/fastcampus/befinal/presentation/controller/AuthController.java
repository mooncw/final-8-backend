package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AuthFacade;
import com.fastcampus.befinal.common.response.ApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fastcampus.befinal.common.response.success.info.JwtSuccessCode.REISSUE_JWT_TOKEN;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<AuthDto.ReissueJwtResponse>> reissueAccessToken(
        @RequestBody
        AuthDto.ReissueJwtRequest request) {
        AuthDto.ReissueJwtResponse response = authFacade.reissueJwt(request);
        return ResponseEntityFactory.toResponseEntity(REISSUE_JWT_TOKEN, response);
    }
}
