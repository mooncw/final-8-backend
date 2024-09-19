package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class AdminInfo {
    @Builder
    public record SignUpUserInfo(
        Long id,
        String name,
        String empNumber,
        String phoneNumber,
        String email,
        LocalDateTime signUpDateTime
    ) {}

    @Builder
    public record UserInfo(
        Long id,
        String empNumber,
        String name,
        String role,
        String userId,
        String phoneNumber,
        String email,
        LocalDateTime signUpDateTime,
        LocalDateTime finalLoginDateTime
    ) {}

    @Builder
    public record UserTaskInfo(
        Long id,
        String empNumber,
        String name,
        Integer totalAd,
        Integer notDoneAd,
        Integer doneAd,
        Double doneRatio
    ) {}

    @Builder
    public record UnassignedAdInfo(
        String adId,
        String product,
        String advertiser,
        String category
    ) {}

    @Builder
    public record AssigneeInfo(
        Long id,
        String empNo,
        String name,
        Integer additionalTaskCount
    ) {}

    @Builder
    public record AssigneeListInfo(
        List<AssigneeInfo> assigneeList
    ) {
        public static AssigneeListInfo from(List<AssigneeInfo> info) {
            return AssigneeListInfo.builder()
                .assigneeList(info)
                .build();
        }
    }
}
