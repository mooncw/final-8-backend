package com.fastcampus.befinal.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private String id;

    private String password;

    private List<SimpleGrantedAuthority> authorities;

    @Builder
    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.authorities = new ArrayList<>();
        this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }


}
