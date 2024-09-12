package com.fastcampus.befinal.infrastructure.redis.mapper;

import com.fastcampus.befinal.domain.entity.RefreshToken;
import com.fastcampus.befinal.domain.entity.SmsCertification;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserId;
import com.fastcampus.befinal.domain.info.JwtInfo;
import com.fastcampus.befinal.domain.info.SmsInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RedisEntityMapper {
    RefreshToken from(JwtInfo.RefreshTokenInfo info);

    SmsCertification from(SmsInfo.SmsCertificationInfo info);

    UserId from(User user);
}
