package com.fastcampus.befinal.domain.command;

import com.fastcampus.befinal.domain.entity.User;
import lombok.Builder;

public class UserCommand {
    @Builder
    public record UserUpdateRequest(
        User user,
        String phoneNumber,
        String email,
        String certNoCheckToken
    ){}

    @Builder
    public record PasswordUpdateRequest(
        User user,
        String password,
        String currentPassword,
        String newPassword
    ){}
}
