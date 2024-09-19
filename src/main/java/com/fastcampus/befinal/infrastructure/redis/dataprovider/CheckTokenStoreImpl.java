package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenStore;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.UserId;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.infrastructure.redis.mapper.RedisEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

import static com.fastcampus.befinal.common.contant.SmsConstant.CHECK_TOKEN_DURATION;

@DataProvider
@RequiredArgsConstructor
public class CheckTokenStoreImpl implements CheckTokenStore {
    private final RedisTemplate<String, RedisValue> redisTemplate;
    private final RedisEntityMapper redisEntityMapper;

    @Override
    public void store(AuthInfo.CheckTokenInfo info) {
        redisTemplate.opsForValue().set(info.token(), null, getDuration());
    }

    @Override
    public void store(AuthInfo.CheckTokenInfo info, String userId) {
        UserId certifiedUserId = redisEntityMapper.from(userId);
        redisTemplate.opsForValue().set(info.token(), certifiedUserId, getDuration());
    }

    @Override
    public void delete(AuthInfo.CheckTokenInfo info) {
        redisTemplate.delete(info.token());
    }

    private Duration getDuration() {
        return Duration.ofMinutes(CHECK_TOKEN_DURATION);
    }
}
