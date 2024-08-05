package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.User;

public interface UserReader {
    User findUser(String userId);
}
