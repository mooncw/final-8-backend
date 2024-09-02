package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.command.UserCommand;
import lombok.Builder;

import java.util.Optional;

public class UserInfo {

    @Builder
    public record UserUpdateInfo(
        String id,
        String email,
        String phoneNumber
    ){
        public static UserUpdateInfo from(UserCommand.UserUpdateRequest command){
            return UserUpdateInfo.builder()
                .id(command.user().getId())
                .email(Optional.ofNullable(command.email())
                    .orElse(command.user().getEmail()))
                .phoneNumber(Optional.ofNullable(command.phoneNumber())
                    .orElse(command.user().getPhoneNumber()))
                .build();
        }
    }

    @Builder
    public record PasswordUpdateInfo(
        String id,
        String password
    ){
        public static PasswordUpdateInfo from(UserCommand.PasswordUpdateRequest command){
            return PasswordUpdateInfo.builder()
                .id(command.id())
                .password(command.newPassword())
                .build();
        }
    }
}
