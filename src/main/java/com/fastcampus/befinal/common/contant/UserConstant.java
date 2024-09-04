package com.fastcampus.befinal.common.contant;

import java.time.LocalDateTime;

public class UserConstant {
    public static final LocalDateTime INITIAL_FINAL_LOGIN_DATETIME = LocalDateTime.of(1000, 1, 1, 0, 0, 0);

    // DTO 검증
    public static final String INVALID_USER_UPDATE_REQUEST = "전화번호와 이메일을 다시 확인해주세요";

    //Swagger example
    public static final String SWAGGER_UPDATE_PHONE_NUMBER = "01011113333";
    public static final String SWAGGER_UPDATE_EMAIL = "gildong@hong.com";

    public static final String SWAGGER_CURRENT_USER_PASSWORD = "aaaaaaa1";
    public static final String SWAGGER_NEW_USER_PASSWORD = "aaaaaaa2";
    public static final String SWAGGER_UPDATE_CERTIFICATION_NUMBER_CHECK_TOKEN = "5gf43709-d81e-4a53-b447-c50fa6ec06e4";

    public static final String SWAGGER_CERTIFICATION_NUMBER_TYPE_UPDATE = "UpdateUser";
}
