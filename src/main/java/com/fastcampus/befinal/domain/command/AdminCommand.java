package com.fastcampus.befinal.domain.command;

import com.fastcampus.befinal.common.type.UserTaskSortType;
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

    @Builder
    public record RejectUser(
        String empNo
    ) {}

    @Builder
    public record RejectUserRequest(
        List<RejectUser> userList
    ) {}

    @Builder
    public record FindUserTaskListRequest(
        Integer cursorId,
        UserTaskSortType sorted,
        String period
    ) {}

    @Builder
    public record SelectedAssigneeInfo(
        Long id,
        Long taskAssignmentAmount
    ) {}

    @Builder
    public record AssignTaskRequest(
        List<SelectedAssigneeInfo> selectedAssigneeList
    ) {}

    @Builder
    public record FindUserTaskDetailListRequest(
        String cursorId,
        Long id,
        String period,
        Boolean state
    ) {}
}
