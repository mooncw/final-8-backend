package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDateTime;

public class AdminInfo {
    @Builder
    public record SignUpUserInfo(
        Long id,
        String name,
        String empNumber,
        String phoneNumber,
        String email,
        LocalDateTime signUpDateTime
    ) {}

    @Builder
    public record UserInfo(
        Long id,
        String empNumber,
        String name,
        String role,
        String userId,
        String phoneNumber,
        String email,
        LocalDateTime signUpDateTime,
        LocalDateTime finalLoginDateTime
    ) {}

    @Builder
    public record UserTaskInfo(
        Long id,
        String empNumber,
        String name,
        Integer totalAd,
        Integer notDoneAd,
        Integer DoneAd
    ) {}
}
