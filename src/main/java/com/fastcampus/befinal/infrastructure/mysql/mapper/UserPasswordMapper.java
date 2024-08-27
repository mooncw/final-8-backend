package com.fastcampus.befinal.infrastructure.mysql.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPasswordMapper {
    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String ecodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
