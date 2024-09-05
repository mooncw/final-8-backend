package com.fastcampus.befinal.infrastructure.mysql.mapper;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

import static com.fastcampus.befinal.common.contant.AuthConstant.USER_AUTHORITY;
import static com.fastcampus.befinal.common.contant.UserConstant.INITIAL_FINAL_LOGIN_DATETIME;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = UserPasswordMapper.class
)
public interface MysqlEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "empNo", target = "empNumber")
    @Mapping(target = "signUpDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    UserManagement from(AuthCommand.SignUpRequest command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "signUpDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "finalLoginDateTime", expression = "java(initializeFinalLoginDateTime())")
    @Mapping(target = "role", constant = USER_AUTHORITY)
    User from(UserManagement userManagement);

    default LocalDateTime initializeFinalLoginDateTime() {
        return INITIAL_FINAL_LOGIN_DATETIME;
    }
}
