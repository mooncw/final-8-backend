package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.UserReader;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.NOT_FOUND_USER;

@DataProvider
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;

    @Override
    public User findUser(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }
}
