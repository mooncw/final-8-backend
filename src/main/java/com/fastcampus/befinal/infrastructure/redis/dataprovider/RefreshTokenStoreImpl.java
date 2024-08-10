package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.fastcampus.befinal.common.contant.RedisConstant.REFRESHTOKEN_PREFIX;

@DataProvider
@RequiredArgsConstructor
public class RefreshTokenStoreImpl implements RefreshTokenStore {
    private final RedisTemplate<String, RefreshToken> redisTemplate;

    @Override
    public void store(RefreshToken refreshToken) {
        Duration duration = getDuration(refreshToken);

        redisTemplate.opsForValue().set(REFRESHTOKEN_PREFIX + refreshToken.getUserId(), refreshToken, duration);
    }

    private Duration getDuration(RefreshToken refreshToken) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationTime = refreshToken.getExpirationTime();

        return Duration.between(now, expirationTime);
    }
}
