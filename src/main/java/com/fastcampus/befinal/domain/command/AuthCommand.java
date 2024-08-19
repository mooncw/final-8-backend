package com.fastcampus.befinal.domain.command;

public class AuthCommand {
    public record SignInRequest(
        String userId,
        String password
    ) {}
}
