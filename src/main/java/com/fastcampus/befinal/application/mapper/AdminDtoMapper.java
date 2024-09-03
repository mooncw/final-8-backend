package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminDto.FindSignUpUserListResponse from(ScrollPagination<Long, AdminInfo.SignUpUserInfo> info);

    @Mapping(source = "id", target = "cursorId")
    @Mapping(source = "empNumber", target = "empNo")
    @Mapping(source = "signUpDateTime", target = "signUpRequestDateTime")
    AdminDto.SignUpUserInfo from(AdminInfo.SignUpUserInfo info);

    default List<AdminDto.SignUpUserInfo> mapSignUpUserInfoList(List<AdminInfo.SignUpUserInfo> info) {
        return info.stream()
            .map(this::from)
            .toList();
    }
}
