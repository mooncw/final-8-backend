package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.AdminDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.service.AdminService;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StopWatch;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final AdminService adminService;
    private final AdminDtoMapper adminDtoMapper;

    public void approveUser(AdminDto.ApproveUserRequest request) {
        adminService.approveUser(adminDtoMapper.toAdminCommand(request));
    }

    public void rejectUser(AdminDto.RejectUserRequest request) {
        adminService.rejectUser(adminDtoMapper.toAdminCommand(request));
    }

    public AdminDto.FindSignUpUserListResponse findSignUpUserScroll(Long cursorId) {
        return adminDtoMapper.fromSignUpUserScroll(adminService.findSignUpUserScroll(cursorId));
    }

    public AdminDto.FindUserListResponse findUserScroll(Long cursorId) {
        return adminDtoMapper.fromUserScroll(adminService.findUserScroll(cursorId));
    }

    public void deleteUser(Long userId) {
        adminService.deleteUser(userId);
    }

    public AdminDto.FindUserTaskListResponse findUserTaskScroll(AdminDto.FindUserTaskListRequest request) {
        ScrollPagination<Integer, AdminInfo.UserTaskInfo> info =
            adminService.findUserTaskScroll(adminDtoMapper.toAdminCommand(request));
        return adminDtoMapper.fromUserTaskScroll(info);
    }

    public AdminDto.FindUnassignedAdListResponse findUnassignedAdScroll(String cursorId) {
        return adminDtoMapper.fromUnassignedAdScroll(adminService.findUnassignedAdScroll(cursorId));
    }

    public void assignTask(AdminDto.AssignTaskRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        adminService.assignTask(adminDtoMapper.toAdminCommand(request));
        stopWatch.stop();
        System.out.println("V3 Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    public void assignTaskOriginal(AdminDto.AssignTaskRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        adminService.assignTaskOriginal(adminDtoMapper.toAdminCommand(request));
        stopWatch.stop();
        System.out.println("Original Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    public void assignTaskV1(AdminDto.AssignTaskRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        adminService.assignTaskV1(adminDtoMapper.toAdminCommand(request));
        stopWatch.stop();
        System.out.println("V1 Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    public void assignTaskV2(AdminDto.AssignTaskRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        adminService.assignTaskV2(adminDtoMapper.toAdminCommand(request));
        stopWatch.stop();
        System.out.println("V2 Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
