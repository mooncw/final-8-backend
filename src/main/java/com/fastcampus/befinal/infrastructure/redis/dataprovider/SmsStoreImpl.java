package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.SmsStore;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.info.SmsInfo;
import com.fastcampus.befinal.infrastructure.redis.mapper.RedisEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

import static com.fastcampus.befinal.common.contant.RedisConstant.SMSCERTIFICATION_PREFIX;
import static com.fastcampus.befinal.common.contant.SmsConstant.CERTIFICATION_DURATION;

@DataProvider
@RequiredArgsConstructor
public class SmsStoreImpl implements SmsStore {
    private final RedisTemplate<String, RedisValue> redisTemplate;
    private final RedisEntityMapper redisEntityMapper;

    @Override
    public void store(SmsInfo.SmsCertificationInfo info) {
        SmsCertification smsCertification = redisEntityMapper.from(info);

        Duration duration = getDuration();

        redisTemplate.opsForValue().set(SMSCERTIFICATION_PREFIX + info.phoneNumber(), smsCertification, duration);
    }

    private Duration getDuration() {
        return Duration.ofMinutes(CERTIFICATION_DURATION);
    }
}
