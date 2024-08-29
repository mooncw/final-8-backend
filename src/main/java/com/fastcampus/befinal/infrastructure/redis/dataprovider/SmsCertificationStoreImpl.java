package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.SmsCertificationStore;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.info.SmsInfo;
import com.fastcampus.befinal.infrastructure.redis.mapper.RedisEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.fastcampus.befinal.common.contant.SmsConstant.CERTIFICATION_DURATION;

@DataProvider
@RequiredArgsConstructor
public class SmsCertificationStoreImpl implements SmsCertificationStore {
    private final RedisTemplate<String, RedisValue> redisTemplate;
    private final RedisEntityMapper redisEntityMapper;

    @Override
    public void store(SmsInfo.SmsCertificationInfo info) {
        SmsCertification smsCertification = redisEntityMapper.from(info);

        Duration duration = getDuration(info);

        redisTemplate.opsForValue().set(info.type() + "_" + info.phoneNumber(), smsCertification, duration);
    }

    private Duration getDuration(SmsInfo.SmsCertificationInfo info) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationTime = info.requestTime().plusMinutes(CERTIFICATION_DURATION);

        return Duration.between(now, expirationTime);
    }
}
