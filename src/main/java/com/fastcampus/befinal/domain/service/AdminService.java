package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.AdminCommand;

public interface AdminService {
    void approveUser(AdminCommand.ApproveUserRequest command);
}
