package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenReader;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.info.JwtInfo;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@DisplayName("jwtCreationService 테스트")
@ExtendWith(MockitoExtension.class)
class JwtCreationServiceImplTest {
    @InjectMocks
    private JwtCreationServiceImpl jwtCreationService;

    @Mock
    private RefreshTokenStore refreshTokenStore;

    @Mock
    private RefreshTokenReader refreshTokenReader;

    private long accessTokenValidityInSeconds;
    private long refreshTokenValidityInSeconds;

    @BeforeEach
    public void setUp() {
        String secret = Generator.generate(45);

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        ReflectionTestUtils.setField(jwtCreationService, "key", Keys.hmacShaKeyFor(keyBytes));
    }

    @Test
    @DisplayName("JWT 생성 테스트")
    void createJwtTest() {
        //setUp
        accessTokenValidityInSeconds = 3600L;
        refreshTokenValidityInSeconds = 1209600L;

        ReflectionTestUtils.setField(jwtCreationService, "accessTokenValidityInSeconds", accessTokenValidityInSeconds);
        ReflectionTestUtils.setField(jwtCreationService, "refreshTokenValidityInSeconds", refreshTokenValidityInSeconds);

        //given
        JwtCommand.CreateJwtRequest command = JwtCommand.CreateJwtRequest.builder()
            .userId("A12")
            .build();

        doNothing()
            .when(refreshTokenStore)
            .store(any(JwtInfo.RefreshTokenInfo.class));

        //when
        JwtInfo.TokenInfo jwtInfo = jwtCreationService.createJwt(command);

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

    @Test
    @DisplayName("JWT 재발급 테스트")
    void reissueJwtTest() {
        //setUp
        accessTokenValidityInSeconds = 0L;
        refreshTokenValidityInSeconds = 31557600000L;

        ReflectionTestUtils.setField(jwtCreationService, "accessTokenValidityInSeconds", accessTokenValidityInSeconds);
        ReflectionTestUtils.setField(jwtCreationService, "refreshTokenValidityInSeconds", refreshTokenValidityInSeconds);

        //given
        JwtCommand.CreateJwtRequest createCommand = JwtCommand.CreateJwtRequest.builder()
            .userId("A12")
            .build();

        doNothing()
            .when(refreshTokenStore)
            .store(any(JwtInfo.RefreshTokenInfo.class));

        JwtInfo.TokenInfo createJwtInfo = jwtCreationService.createJwt(createCommand);

        JwtCommand.ReissueJwtRequest reissueCommand = JwtCommand.ReissueJwtRequest.builder()
            .accessToken(createJwtInfo.accessToken())
            .refreshToken(createJwtInfo.refreshToken())
            .build();

        RefreshToken token = RefreshToken.builder()
            .token(createJwtInfo.refreshToken())
            .build();

        doReturn(token)
            .when(refreshTokenReader)
            .find(anyString());

        //when
        JwtInfo.TokenInfo reissueJwtInfo = jwtCreationService.reissueJwt(reissueCommand);

        //then
        String accessToken = reissueJwtInfo.accessToken();
        String refreshToken = reissueJwtInfo.refreshToken();

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