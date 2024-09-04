package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminCommand.RejectUserRequest toAdminCommand(AdminDto.RejectUserRequest request);
}
