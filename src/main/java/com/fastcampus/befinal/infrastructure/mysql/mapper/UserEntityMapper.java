package com.fastcampus.befinal.infrastructure.mysql.mapper;

import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.UserInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = UserPasswordMapper.class
)
public interface UserEntityMapper {
    User from(UserInfo.UserUpdateInfo userInfo);
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User from(UserInfo.PasswordUpdateInfo userInfo);
}
