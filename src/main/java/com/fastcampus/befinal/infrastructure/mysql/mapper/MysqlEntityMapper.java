package com.fastcampus.befinal.infrastructure.mysql.mapper;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = UserPasswordMapper.class
)
public interface MysqlEntityMapper {
    @Mapping(source = "empNo", target = "empNumber")
    @Mapping(target = "signUpDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    UserManagement from(AuthCommand.SignUpRequest command);

    @Mapping(target = "signUpDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "finalLoginDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "role", constant = USER_AUTHORITY)
    User from(UserManagement userManagement);
}
