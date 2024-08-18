package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenReader;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.info.JwtInfo;
import com.fastcampus.befinal.domain.service.JwtCreationService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static com.fastcampus.befinal.common.contant.JwtConstant.JWT_USER_KEY;
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
    public JwtInfo.TokenInfo createJwt(JwtCommand.CreateJwtRequest command) {
        JwtInfo.UserInfo user = JwtInfo.UserInfo.from(command.userId());
        return JwtInfo.TokenInfo.of(createAccessToken(user), createRefreshToken(user));
    }

    private String createAccessToken(JwtInfo.UserInfo user) {
        Claims claims = Jwts.claims();
        claims.put(JWT_USER_KEY, user.id());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusSeconds(accessTokenValidityInSeconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private String createRefreshToken(JwtInfo.UserInfo user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDateTime = now.plusSeconds(refreshTokenValidityInSeconds);

        String jwt = Jwts.builder()
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        JwtInfo.RefreshTokenInfo refreshTokenInfo = JwtInfo.RefreshTokenInfo.of(user.id(), jwt, now, expirationDateTime);

        refreshTokenStore.store(refreshTokenInfo);

        return refreshTokenInfo.token();
    }

    @Override
    public JwtInfo.TokenInfo reissueJwt(JwtCommand.ReissueJwtRequest command) {
        String userId = parseUserIdFromExpiredAccessToken(command.accessToken());

        RefreshToken refreshToken = refreshTokenReader.find(userId);

        validateRefreshToken(command, refreshToken);

        JwtInfo.UserInfo user = JwtInfo.UserInfo.from(userId);

        return JwtInfo.TokenInfo.of(createAccessToken(user), createRefreshToken(user));
    }

    private void validateRefreshToken(JwtCommand.ReissueJwtRequest command, RefreshToken refreshTokenObj) {
        String requestRefreshToken = command.refreshToken();
        String storeRefreshToken = refreshTokenObj.getToken();

        if (!Objects.equals(requestRefreshToken, storeRefreshToken)) {
            throw new BusinessException(INCONSISTENT_REFRESHTOKEN);
        }
    }

    private String parseUserIdFromExpiredAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            throw new BusinessException(NOT_EXPIRED_ACCESSTOKEN);
        } catch (SecurityException | MalformedJwtException e) {
            throw new BusinessException(NOT_VALID_JWT);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get(JWT_USER_KEY, String.class);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ILLEGAL_JWT);
        }
    }
}
