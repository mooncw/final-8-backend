package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

public class UserInfo {

    @Builder
    public record UserUpdateInfo(
        String id,
        String email,
        String phoneNumber
    ){
        public static UserUpdateInfo from(UserCommand.UserUpdateRequest command){
            return UserUpdateInfo.builder()
                .id(command.id())
                .email(command.email())
                .phoneNumber(command.phoneNumber())
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
