package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.application.mapper.JwtDtoMapper;
import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenStore;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.info.JwtInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.fastcampus.befinal.common.contant.RedisConstant.REFRESHTOKEN_PREFIX;

@DataProvider
@RequiredArgsConstructor
public class RefreshTokenStoreImpl implements RefreshTokenStore {
    private final RedisTemplate<String, RedisValue> redisTemplate;
    private final JwtDtoMapper jwtDtoMapper;

    @Override
    public void store(JwtInfo.RefreshTokenInfo info) {
        RefreshToken refreshToken = jwtDtoMapper.from(info);

        Duration duration = getDuration(info);

        redisTemplate.opsForValue().set(REFRESHTOKEN_PREFIX + info.userId(), refreshToken, duration);
    }

    private Duration getDuration(JwtInfo.RefreshTokenInfo info) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = info.expirationDateTime();

        return Duration.between(now, expirationTime);
    }
}
