package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@DisplayName("jwtCreationService 테스트")
@ExtendWith(MockitoExtension.class)
class JwtCreationServiceImplTest {
    @InjectMocks
    private JwtCreationServiceImpl jwtCreationService;

    @Mock
    private RefreshTokenStore refreshTokenStore;

    @BeforeEach
    public void setUp() {
        String secret = Generator.generate(45);
        long accessTokenValidityInSeconds = 3600;
        long refreshTokenValidityInSeconds = 1_209_600;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        ReflectionTestUtils.setField(jwtCreationService, "key", Keys.hmacShaKeyFor(keyBytes));
        ReflectionTestUtils.setField(jwtCreationService, "accessTokenValidityInSeconds", accessTokenValidityInSeconds);
        ReflectionTestUtils.setField(jwtCreationService, "refreshTokenValidityInSeconds", refreshTokenValidityInSeconds);
    }

    @Test
    @DisplayName("JWT 생성 테스트")
    void createJwtTest() {
        //given
        AuthCommand.CreateJwtRequest command = AuthCommand.CreateJwtRequest.builder()
            .userId("A12")
            .build();

        doNothing()
            .when(refreshTokenStore)
            .store(any(RefreshToken.class));

        //when
        AuthInfo.JwtInfo jwtInfo = jwtCreationService.createJwt(command);

        //then
        String accessToken = jwtInfo.accessToken();
        String refreshToken = jwtInfo.refreshToken();

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