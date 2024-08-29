package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class UserCommand {
    @Builder
    public record UserUpdateRequest(
        String id,
        String phoneNumber,
        String email
    ){}

    @Builder
    public record PasswordUpdateRequest(
        String id,
        String currentPassword,
        String newPassword
    ){}
}
