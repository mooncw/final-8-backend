package com.fastcampus.befinal.common.contant;

import java.time.LocalDateTime;

public class UserConstant {
    public static final LocalDateTime INITIAL_FINAL_LOGIN_DATETIME = LocalDateTime.of(1000, 1, 1, 0, 0, 0);

    // DTO 검증
    public static final String INVALID_USER_UPDATE_REQUEST = "전화번호와 이메일을 다시 확인해주세요";
}
