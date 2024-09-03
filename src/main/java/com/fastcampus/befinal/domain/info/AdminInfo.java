package com.fastcampus.befinal.domain.info;

import lombok.Builder;

public class AdminInfo {
    @Builder
    public record FindSignUpUserListInfo(

    ) {}

    @Builder
    public record SignUpUserInfo(
        Long id,
        String name,
        String empNumber,
        String phoneNumber,
        String email,
        String signUpRequestDateTime
    ) {}
}
