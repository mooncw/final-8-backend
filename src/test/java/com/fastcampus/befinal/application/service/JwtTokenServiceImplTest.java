package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("JwtTokenService 테스트")
@ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {
    @InjectMocks
    private JwtTokenServiceImpl jwtTokenService;

    @BeforeEach
    public void setUp() {
        String secret = "G7jK2LqF9xR6tN3vB8pW1yZ0oP4uV5rH9mT6Q7lX2cY8wR1jV3zN4kL5dY0x";
        long accessTokenValidityInSeconds = 3600;
        long refreshTokenValidityInSeconds = 1_209_600;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        ReflectionTestUtils.setField(jwtTokenService, "key", Keys.hmacShaKeyFor(keyBytes));
        ReflectionTestUtils.setField(jwtTokenService, "accessTokenValidityInSeconds", accessTokenValidityInSeconds);
        ReflectionTestUtils.setField(jwtTokenService, "refreshTokenValidityInSeconds", refreshTokenValidityInSeconds);
    }


        @Test
    @DisplayName("jwt 토큰 생성 테스트")
    void testCreateToken() {
        UserInfo user = UserInfo.builder()
            .ID("ASD")
            .build();

        TokenInfo tokenInfo = jwtTokenService.createToken(user);

        System.out.println(tokenInfo.getAccessToken());
        System.out.println(tokenInfo.getRefreshToken());
    }
}