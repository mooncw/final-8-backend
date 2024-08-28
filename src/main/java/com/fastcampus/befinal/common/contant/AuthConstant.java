package com.fastcampus.befinal.common.contant;

public class AuthConstant {
    //권한
    public static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    public static final String USER_AUTHORITY = "ROLE_USER";

    //Dto 검증
    public static final String NOT_BLANK_ACCESSTOKEN = "AccessToken을 입력해 주세요.";
    public static final String NOT_BLANK_REFRESHTOKEN = "RefreshToken을 입력해 주세요.";
    public static final String NOT_BLANK_USER_NAME = "이름을 입력해 주세요.";
    public static final String SIZE_MISMATCH_USER_NAME = "이름의 글자 수를 확인해 주세요.";
    public static final String PATTERN_MISMATCH_USER_NAME = "이름에 입력 가능한 패턴을 확인해 주세요.";
    public static final String NOT_BLANK_PHONE_NUMBER = "연락처를 입력해 주세요.";
    public static final String SIZE_MISMATCH_PHONE_NUMBER = "연락처의 글자 수를 확인해 주세요.";
    public static final String PATTERN_MISMATCH_PHONE_NUMBER = "연락처에 입력 가능한 패턴을 확인해 주세요.";
    public static final String NOT_BLANK_USER_ID = "아이디를 입력해 주세요.";
    public static final String SIZE_MISMATCH_USER_ID = "아이디의 글자 수를 확인해 주세요.";
    public static final String PATTERN_MISMATCH_USER_ID = "아이디에 입력 가능한 패턴을 확인해 주세요.";
    public static final String NOT_BLANK_USER_PASSWORD = "비밀번호를 입력해 주세요.";
    public static final String SIZE_MISMATCH_USER_PASSWORD = "비밀번호의 글자 수를 확인해 주세요.";
    public static final String PATTERN_MISMATCH_USER_PASSWORD = "비밀번호에 입력 가능한 패턴을 확인해 주세요.";
    public static final String NOT_BLANK_USER_EMP_NUMBER = "사원번호를 입력해 주세요.";
    public static final String SIZE_MISMATCH_USER_EMP_NUMBER = "사원번호의 글자 수를 확인해 주세요.";
    public static final String PATTERN_MISMATCH_USER_EMP_NUMBER = "사원번호에 입력 가능한 패턴을 확인해 주세요.";
    public static final String NOT_BLANK_USER_EMAIL = "이메일을 입력해 주세요.";
    public static final String INVALID_FORMAT_USER_EMAIL = "이메일 형식으로 입력해 주세요.";
    public static final String NOT_EMPTY_USER_LIST = "1명 이상의 유저를 입력해 주세요.";


    //Swagger reissue token example
    public static final String SWAGGER_REISSUE_REQUEST_ACCESSTOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0ZW1wb3JhcnlVc2VyMSIsImlhdCI6MTcyNDA2MzczMiwiZXhwIjoxNzI0MDYzNzMyfQ.aRt1303R3GWM-3KKhkXnS8FofM_OxOeUabNcnOeg7UI";
    public static final String SWAGGER_REISSUE_REQUEST_REFRESHTOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjQwNjM3MzIsImV4cCI6MzMyODE2NjM3MzJ9.BbE_yPIx03y_RT5K78rEBzCyqBwnb9oJ_urO7mgjiDA";
    public static final String SWAGGER_REISSUE_RESPONSE_ACCESSTOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0ZW1wb3JhcnlVc2VyMSIsImlhdCI6MTcyNDA2NDQzNiwiZXhwIjoxNzI0MDY0NDM2fQ.fVLaomRrNk56fFNQrTNL33HK22cYdRfZJBPZqIJ5dKo";
    public static final String SWAGGER_REISSUE_RESPONSE_REFRESHTOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjQwNjQ0MzYsImV4cCI6MzMyODE2NjQ0MzZ9.jZqipnoDE6SdSMh-YCqqfpLRWHqFUJqJdZjjgbVULu0";

    //Swagger signUp & checkIdDuplication example
    public static final String SWAGGER_SIGN_UP_USER_NAME = "홍길동";
    public static final String SWAGGER_SIGN_UP_PHONE_NUMBER = "01011112222";
    public static final String SWAGGER_SIGN_UP_USER_ID = "abcd";
    public static final String SWAGGER_SIGN_UP_USER_PASSWORD = "aaaaaaa1";
    public static final String SWAGGER_SIGN_UP_USER_EMP_NOMBER = "11111111";
    public static final String SWAGGER_SIGN_UP_USER_EMAIL = "hong@hong.com";

}
