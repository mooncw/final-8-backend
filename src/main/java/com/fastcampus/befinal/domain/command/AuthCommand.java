package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class AuthCommand {
    @Builder
    public record SignUpRequest(
        String name,
        String phoneNumber,
        String id,
        String password,
        String empNo,
        String email
    ) {}

    @Builder
    public record CheckIdDuplicationRequest(
        String id
    ) {}

    @Builder
    public record CheckCertificationNumberRequest(
        String certificationNumber
    ) {}

    public record SignInRequest(
        String userId,
        String password
    ) {}
}
