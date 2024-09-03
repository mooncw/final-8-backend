package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.AdminDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.service.AdminService;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final AdminService adminService;
    private final AdminDtoMapper adminDtoMapper;

    public void approveUser(AdminDto.ApproveUserRequest request) {
        adminService.approveUser(adminDtoMapper.toAdminCommand(request));
    }

    public AdminDto.FindSignUpUserListResponse findSignUpUserScroll(Long cursorId) {
        return adminDtoMapper.from(adminService.findSignUpUserScroll(cursorId));
    }
}
