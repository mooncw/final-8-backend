package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDateTime;

public class AdminInfo {
    @Builder
    public record UserInfo(
        String empNumber,
        String name,
        String role,
        String id,
        String phoneNumber,
        String email,
        LocalDateTime signUpDateTime,
        LocalDateTime finalLoginDateTime
    ) {}
}
