package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @PutMapping("/info")
    public ResponseEntity<AppApiResponse> updateUserInfo(
        @RequestBody
        UserDto.UserUpdateRequest request,
        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updateUser(userDetails, request);
        return ResponseEntityFactory.toResponseEntity();
    }

    @PutMapping("/password")
    public ResponseEntity<AppApiResponse> updatePassword(
        @RequestBody
        UserDto.PasswordUpdateRequest request,
        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updatePassword(userDetails, request);
        return ResponseEntityFactory.toResponseEntity();
    }
}
