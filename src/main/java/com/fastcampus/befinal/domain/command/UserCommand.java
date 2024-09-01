package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class UserCommand {
    @Builder
    public record UserUpdateRequest(
        String id,
        String phoneNumber,
        String email,
        String certificationNumberCheckToken
    ){}

    @Builder
    public record PasswordUpdateRequest(
        String id,
        String password,
        String currentPassword,
        String newPassword
    ){}
}
