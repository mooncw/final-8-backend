package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.UserReader;
import com.fastcampus.befinal.domain.entity.User;

import java.util.List;
import java.util.Optional;

@DataProvider
public class UserReaderImpl implements UserReader {
    private static final List<String> userIdList = List.of("temporaryUser");

    @Override
    public User findUser(String userId) {
        Optional<String> user = userIdList.stream()
            .filter(u -> u.equals(userId))
            .findFirst();

        user.orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다."));

        return User.builder()
            .id("temporaryUser")
            .password("temporaryUser")
            .build();
    }
}
