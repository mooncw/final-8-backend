package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsCertificationReader;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static com.fastcampus.befinal.common.contant.RedisConstant.SMSCERTIFICATION_PREFIX;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.NOT_FOUND_CERTIFICATION_NUMBER;

@DataProvider
@RequiredArgsConstructor
public class SmsCertificationReaderImpl implements SmsCertificationReader {
    private final RedisTemplate<String, RedisValue> redisTemplate;

    @Override
    public SmsCertification find(AuthCommand.CheckCertificationNumberRequest command) {
        return (SmsCertification) Optional.ofNullable(redisTemplate.opsForValue()
                .get(SMSCERTIFICATION_PREFIX + command.phoneNumber()))
            .orElseThrow(() -> new BusinessException(NOT_FOUND_CERTIFICATION_NUMBER));
    }
}
