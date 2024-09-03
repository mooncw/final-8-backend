package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.AuthConstant.ADMIN_AUTHORITY_NAME;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_AUTHORITY;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminDto.FindUserListResponse from(ScrollPagination<AdminInfo.UserInfo> info);

    @Mapping(source = "empNumber", target = "empNo")
    @Mapping(target = "authority", expression = "java(mapRoleToAuthorityName(info))")
    @Mapping(source = "signUpDateTime", target = "signUpDate")
    AdminDto.UserInfo from(AdminInfo.UserInfo info);

    default String mapRoleToAuthorityName(AdminInfo.UserInfo info) {
        switch (info.role()) {
            case USER_AUTHORITY -> {
                return USER_AUTHORITY_NAME;
            }
            case ADMIN_AUTHORITY -> {
                return ADMIN_AUTHORITY_NAME;
            }
            default -> throw new BusinessException(INVALID_AUTHORITY);
        }
    }

    default List<AdminDto.UserInfo> mapUserInfoList(List<AdminInfo.UserInfo> info) {
        return info.stream()
            .map(this::from)
            .toList();
    }
}
