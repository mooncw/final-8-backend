package com.fastcampus.befinal.presentation.dto;

import lombok.Builder;

public class UserDto {
    @Builder
    public record UserUpdateRequest(
        String phoneNumber,
        String email
    ){}

    @Builder
    public record PasswordUpdateRequest(
        String currentPassword,
        String newPassword
    ){}
}
