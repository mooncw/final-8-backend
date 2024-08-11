package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenReader;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.service.JwtCreationService;
import com.fastcampus.befinal.presentation.dto.ReissueTokenRequest;
import com.fastcampus.befinal.presentation.dto.ReissueTokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import static com.fastcampus.befinal.common.response.error.info.JwtErrorCode.*;

@Service
@RequiredArgsConstructor
public class JwtCreationServiceImpl implements JwtCreationService {
    private final RefreshTokenStore refreshTokenStore;
    private final RefreshTokenReader refreshTokenReader;

    private Key key;

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityInSeconds;

    @Value("${spring.security.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public TokenInfo createTokenInfo(UserInfo user) {
        return TokenInfo.of(createAccessToken(user), createRefreshToken(user));
    }

    private String createAccessToken(UserInfo user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationTime = now.plusSeconds(accessTokenValidityInSeconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(expirationTime.toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private String createRefreshToken(UserInfo user) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationTime = now.plusSeconds(refreshTokenValidityInSeconds);

        String refreshToken = Jwts.builder()
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(expirationTime.toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        RefreshToken refreshTokenObj = RefreshToken.builder()
            .token(refreshToken)
            .userId(user.getId())
            .creationTime(now)
            .expirationTime(expirationTime)
            .build();

        refreshTokenStore.store(refreshTokenObj);

        return refreshToken;
    }

    @Override
    public ReissueTokenResponse reissueTokenInfo(ReissueTokenRequest request) {
        String userId = parseUserIdFromExpiredAccessToken(request.accessToken());

        RefreshToken refreshTokenObj = refreshTokenReader.find(userId);

        validateRefreshToken(request, refreshTokenObj);

        UserInfo userInfo = UserInfo.builder()
            .id(userId)
            .build();

        TokenInfo tokenInfo = createTokenInfo(userInfo);

        return ReissueTokenResponse.from(tokenInfo);
    }

    private void validateRefreshToken(ReissueTokenRequest request, RefreshToken refreshTokenObj) {
        String requestRefreshToken = request.refreshToken();
        String storeRefreshToken = refreshTokenObj.getToken();

        if (!Objects.equals(requestRefreshToken, storeRefreshToken)) {
            throw new BusinessException(INCONSISTENT_REFRESHTOKEN);
        }
    }

    private String parseUserIdFromExpiredAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            throw new BusinessException(NOT_EXPIRED_JWT);
        } catch (SecurityException | MalformedJwtException e) {
            throw new BusinessException(NOT_VALID_JWT);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get("userId", String.class);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ILLEGAL_JWT);
        }
    }
}
