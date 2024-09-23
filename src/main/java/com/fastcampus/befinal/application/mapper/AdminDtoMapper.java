package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.type.UserTaskSortType;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.contant.UserConstant.INITIAL_FINAL_LOGIN_DATETIME;
import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.INVALID_USER_TASK_SORT_TYPE;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_AUTHORITY;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminDtoMapper {
    AdminCommand.ApproveUserRequest toAdminCommand(AdminDto.ApproveUserRequest request);

    AdminCommand.RejectUserRequest toAdminCommand(AdminDto.RejectUserRequest request);

    @Mapping(source = "sorted", target = "sorted", qualifiedByName = "toSortType")
    AdminCommand.FindUserTaskListRequest toAdminCommand(AdminDto.FindUserTaskListRequest request);

    @Named("toSortType")
    default UserTaskSortType toSortType(String sorted) {
        switch (sorted) {
            case "EmpNo" -> {
                return UserTaskSortType.EMP_NUMBER;
            }
            case "DoneDesc" -> {
                return UserTaskSortType.DONE_DESC;
            }
            case "DoneAsc" -> {
                return UserTaskSortType.DONE_ASC;
            }
            case "DoneRatioDesc" -> {
                return UserTaskSortType.DONE_RATIO_DESC;
            }
            case "DoneRatioAsc" -> {
                return UserTaskSortType.DONE_RATIO_ASC;
            }
            default -> throw new BusinessException(INVALID_USER_TASK_SORT_TYPE);
        }
    }

    AdminCommand.AssignTaskRequest toAdminCommand(AdminDto.AssignTaskRequest request);

    AdminCommand.FindUserTaskDetailListRequest toAdminCommand(AdminDto.FindUserTaskDetailListRequest request);

    AdminDto.FindSignUpUserListResponse fromSignUpUserScroll(ScrollPagination<Long, AdminInfo.SignUpUserInfo> info);

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

    AdminDto.FindUserListResponse fromUserScroll(ScrollPagination<Long, AdminInfo.UserInfo> info);

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return finalLoginDateTime.format(formatter);
    }

    default List<AdminDto.UserInfo> mapUserInfoList(List<AdminInfo.UserInfo> info) {
        return info.stream()
            .map(this::from)
            .toList();
    }

    AdminDto.FindUserTaskListResponse fromUserTaskScroll(ScrollPagination<Integer, AdminInfo.UserTaskInfo> info);

    @Mapping(source = "empNumber", target = "empNo")
    @Mapping(source = "doneRatio", target = "doneRatio", qualifiedByName = "toDoneRatioValue")
    AdminDto.UserTaskInfo from(AdminInfo.UserTaskInfo info);

    @Named("toDoneRatioValue")
    default Integer toDoneRatioValue(Double doneRatio) {
        return (int) Math.round(doneRatio * 100);
    }

    AdminDto.FindUnassignedAdListResponse fromUnassignedAdScroll(ScrollPagination<String, AdminInfo.UnassignedAdInfo> info);

    @Mapping(source = "adId", target = "adId", qualifiedByName = "toAdIdValue")
    AdminDto.UnassignedAdInfo from(AdminInfo.UnassignedAdInfo info);

    @Named("toAdIdValue")
    default String toAdIdValue(String adId) {
        return adId.substring(6);
    }

    AdminDto.FindAssigneeListResponse from(AdminInfo.AssigneeListInfo info);

    AdminDto.AdminFindUserDetailResponse from(AdminInfo.AdminFindUserDetailInfo info);

    @Mapping(target = "role", qualifiedByName = "mapRole")
    AdminDto.UserDetailInfo from(AdminInfo.UserDetailInfo info);

    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "cursorId", source = "currentCursorId")
    @Mapping(target = "userTaskList", source = "contents")
    AdminDto.TaskListResponse from(ScrollPagination<String, AdminInfo.UserTaskDetailInfo> scroll);

    @Mapping(source = "adId", target = "adId", qualifiedByName = "toAdIdValue")
    AdminDto.UserTaskDetailInfo from(AdminInfo.UserTaskDetailInfo info);

    @Named("mapRole")
    default String mapRole(String role) {
        if("ROLE_USER".equals(role)) {
            return "작업자";
        } else if ("ROLE_ADMIN".equals(role)) {
            return "관리자";
        } else {
            return "알 수 없음";
        }
    }
}
