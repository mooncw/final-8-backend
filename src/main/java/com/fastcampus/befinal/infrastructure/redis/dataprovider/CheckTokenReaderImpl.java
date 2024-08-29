package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.info.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@DataProvider
@RequiredArgsConstructor
public class CheckTokenReaderImpl implements CheckTokenReader {
    private final RedisTemplate<String, RedisValue> redisTemplate;

    @Override
    public boolean exists(AuthInfo.CheckTokenInfo info) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(info.token()));
    }
}
