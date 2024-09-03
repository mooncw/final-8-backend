package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.entity.User;
import lombok.Builder;

import java.util.Optional;

public class UserInfo {

    @Builder
    public record UserUpdateInfo(
        User user,
        String email,
        String phoneNumber
    ){
        public static UserUpdateInfo of(User user, String email, String phoneNumber){
            return UserUpdateInfo.builder()
                .user(user)
                .email(Optional.ofNullable(email)
                    .orElse(user.getEmail()))
                .phoneNumber(Optional.ofNullable(phoneNumber)
                    .orElse(user.getPhoneNumber()))
                .build();
        }
    }

    @Builder
    public record PasswordUpdateInfo(
        User user,
        String password
    ){
        public static PasswordUpdateInfo of(User user, String password){
            return PasswordUpdateInfo.builder()
                .user(user)
                .password(password)
                .build();
        }
    }
}
