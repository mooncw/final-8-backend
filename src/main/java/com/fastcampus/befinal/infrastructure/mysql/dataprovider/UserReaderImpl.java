package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.dataprovider.UserReader;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.repository.UserRepository;
import com.fastcampus.befinal.domain.repository.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.NOT_FOUND_USER;

@DataProvider
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;

    @Override
    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    @Override
    public ScrollPagination<Long, AdminInfo.UserInfo> findScroll(Long cursorId) {
        return userRepositoryCustom.findScrollByEmpNumber(cursorId);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }
}
