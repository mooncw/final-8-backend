package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.RefreshTokenReader;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static com.fastcampus.befinal.common.contant.RedisConstant.REFRESHTOKEN_PREFIX;
import static com.fastcampus.befinal.common.response.error.info.JwtErrorCode.NOT_FOUND_REFRESHTOKEN;

@DataProvider
@RequiredArgsConstructor
public class RefreshTokenReaderImpl implements RefreshTokenReader {
    private final RedisTemplate<String, RedisValue> redisTemplate;

    @Override
    public RefreshToken find(String userId) {
        return (RefreshToken) Optional.ofNullable(redisTemplate.opsForValue().get(REFRESHTOKEN_PREFIX + userId))
            .orElseThrow(() -> new BusinessException(NOT_FOUND_REFRESHTOKEN));
    }
}
