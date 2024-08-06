package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.entity.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
public class UserDetailsInfo implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    public static UserDetailsInfo from(User user) {
        return UserDetailsInfo.builder()
            .user(user)
            .build();
    }
}
