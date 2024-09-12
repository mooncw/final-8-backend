package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.entity.RedisValue;
import com.fastcampus.befinal.domain.entity.UserId;
import com.fastcampus.befinal.domain.info.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_PASSWORD_RESET_TOKEN;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.NOT_FOUND_USER;


@DataProvider
@RequiredArgsConstructor
public class CheckTokenReaderImpl implements CheckTokenReader {
    private final RedisTemplate<String, RedisValue> redisTemplate;

    @Override
    public boolean exists(AuthInfo.CheckTokenInfo info) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(info.token()));
    }

    @Override
    public String find(AuthInfo.CheckTokenInfo info) {
        RedisValue value = Optional.ofNullable(redisTemplate.opsForValue().get(info.token()))
            .orElseThrow(() -> new BusinessException(INVALID_PASSWORD_RESET_TOKEN));

        if(value instanceof UserId) {
            return ((UserId)value).getUserId();
        }
        throw new BusinessException(NOT_FOUND_USER);
    }
}
