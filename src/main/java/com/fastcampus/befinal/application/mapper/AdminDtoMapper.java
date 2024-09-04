package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminCommand.RejectUserRequest toAdminCommand(AdminDto.RejectUserRequest request);

    AdminDto.FindSignUpUserListResponse from(ScrollPagination<Long, AdminInfo.SignUpUserInfo> info);

    @Mapping(source = "id", target = "cursorId")
    @Mapping(source = "empNumber", target = "empNo")
    @Mapping(source = "signUpDateTime", target = "signUpRequestDateTime", qualifiedByName = "toSignUpRequestDateTimeValue")
    AdminDto.SignUpUserInfo from(AdminInfo.SignUpUserInfo info);

    @Named("toSignUpRequestDateTimeValue")
    default String toSignUpRequestDateTimeValue(LocalDateTime finalLoginDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return finalLoginDateTime.format(formatter);
    }

    default List<AdminDto.SignUpUserInfo> mapSignUpUserInfoList(List<AdminInfo.SignUpUserInfo> info) {
        return info.stream()
            .map(this::from)
            .toList();
    }
}
