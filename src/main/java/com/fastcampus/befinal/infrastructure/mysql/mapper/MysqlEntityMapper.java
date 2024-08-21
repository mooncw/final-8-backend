package com.fastcampus.befinal.infrastructure.mysql.mapper;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.UserManagement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MysqlEntityMapper {
    @Mapping(source = "empNo", target = "empNumber")
    @Mapping(target = "signUpDateTime", expression = "java(java.time.LocalDateTime.now())")
    UserManagement from(AuthCommand.SignUpRequest command);
}
