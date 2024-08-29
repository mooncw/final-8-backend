package com.fastcampus.befinal.domain.command;

import lombok.Builder;

import java.util.List;

public class AdminCommand {
    @Builder
    public record ApproveUser(
        String empNo
    ) {}

    @Builder
    public record ApproveUserRequest(
        List<ApproveUser> userList
    ) {}
}
