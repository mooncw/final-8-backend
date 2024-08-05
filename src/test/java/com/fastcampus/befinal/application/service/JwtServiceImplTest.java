package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.Generator;
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

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtService 테스트")
@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {
    @InjectMocks
    private JwtServiceImpl JwtService;

    @BeforeEach
    public void setUp() {
        String secret = Generator.generate(45);
        long accessTokenValidityInSeconds = 3600;
        long refreshTokenValidityInSeconds = 1_209_600;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        ReflectionTestUtils.setField(JwtService, "key", Keys.hmacShaKeyFor(keyBytes));
        ReflectionTestUtils.setField(JwtService, "accessTokenValidityInSeconds", accessTokenValidityInSeconds);
        ReflectionTestUtils.setField(JwtService, "refreshTokenValidityInSeconds", refreshTokenValidityInSeconds);
    }

    @Test
    @DisplayName("JWT 생성 테스트")
    void createTokenInfoTest() {
        //given
        UserInfo user = UserInfo.builder()
            .id("ASD")
            .build();

        //when
        TokenInfo tokenInfo = JwtService.createTokenInfo(user);

        //then
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();

        assertThat(accessToken).isNotNull();
        assertThat(refreshToken).isNotNull();

        String[] accessTokenParts = accessToken.split("\\.");
        assertThat(accessTokenParts).hasSize(3);

        String[] refreshTokenParts = refreshToken.split("\\.");
        assertThat(refreshTokenParts).hasSize(3);

        assertThat(Arrays.stream(accessTokenParts)
                        .noneMatch(String::isEmpty))
            .isTrue();

        assertThat(Arrays.stream(refreshTokenParts)
                        .noneMatch(String::isEmpty))
            .isTrue();
    }
}