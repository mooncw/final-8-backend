package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @PutMapping("/info")
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
}
