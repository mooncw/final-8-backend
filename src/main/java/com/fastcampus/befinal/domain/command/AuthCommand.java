package com.fastcampus.befinal.domain.command;

public class AuthCommand {
    public record SignUpRequest(
        String name,
        String phoneNumber,
        String id,
        String password,
        String empNo,
        String email
    ) {}

    public record SignInRequest(
        String userId,
        String password
    ) {}
}
