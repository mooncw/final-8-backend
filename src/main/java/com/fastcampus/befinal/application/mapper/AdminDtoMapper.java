package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.UserConstant.INITIAL_FINAL_LOGIN_DATETIME;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_AUTHORITY;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminDto.FindUserListResponse from(ScrollPagination<Long, AdminInfo.UserInfo> info);

    @Mapping(source = "id", target = "cursorId")
    @Mapping(source = "empNumber", target = "empNo")
    @Mapping(source = "role", target = "authority", qualifiedByName = "toAuthorityName")
    @Mapping(source = "signUpDateTime", target = "signUpDate")
    @Mapping(source = "finalLoginDateTime", target = "finalLoginDateTime", qualifiedByName = "toFinalLoginDateTimeValue")
    AdminDto.UserInfo from(AdminInfo.UserInfo info);

    @Named("toAuthorityName")
    default String toAuthorityName(String role) {
        switch (role) {
            case USER_AUTHORITY -> {
                return USER_AUTHORITY_NAME;
            }
            case ADMIN_AUTHORITY -> {
                return ADMIN_AUTHORITY_NAME;
            }
            default -> throw new BusinessException(INVALID_AUTHORITY);
        }
    }

    @Named("toFinalLoginDateTimeValue")
    default String toFinalLoginDateTimeValue(LocalDateTime finalLoginDateTime) {
        if (finalLoginDateTime.equals(INITIAL_FINAL_LOGIN_DATETIME)) {
            return "-";
        }
        return finalLoginDateTime.toString();
    }

    default List<AdminDto.UserInfo> mapUserInfoList(List<AdminInfo.UserInfo> info) {
        return info.stream()
            .map(this::from)
            .toList();
    }
}
