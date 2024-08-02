package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.info.TokenInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
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
    public TokenInfo createToken(UserInfo user) {
        return TokenInfo.of(createAccessToken(user), createRefreshToken());
    }

    private String createAccessToken(UserInfo user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getID());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(accessTokenValidityInSeconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(tokenValidity.toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private String createRefreshToken() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(refreshTokenValidityInSeconds);

        return Jwts.builder()
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(tokenValidity.toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
